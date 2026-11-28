#!/usr/bin/env python3
"""Regenerate all TG Launcher icon assets from the master emblem PNG."""
import base64
import io
import os
import pathlib

from PIL import Image, ImageDraw, ImageFont

ROOT = pathlib.Path(__file__).resolve().parents[3]
ICONS = ROOT / "packages/oneclient_app/icons"
SRC = ICONS / "logo-source.png"
ASSETS = ROOT / "packages/oneclient_app/assets"
DIST = ROOT / "packages/oneclient_app/distribution"

import shutil
FONT_DIR = next((pathlib.Path(d) / "truetype/dejavu" for d in ["/usr/share/fonts", str(pathlib.Path.home() / ".fonts")] if (pathlib.Path(d) / "truetype/dejavu").exists()), None)
FONT_BOLD = str(FONT_DIR / "DejaVuSans-Bold.ttf")
FONT_REG = str(FONT_DIR / "DejaVuSans.ttf")

src = Image.open(SRC).convert("RGBA")
print("source:", src.size, src.mode)

# keep the master next to the generated icons (not referenced by the packager)
master = src.resize((1024, 1024), Image.LANCZOS) if src.size != (1024, 1024) else src


def render(size):
    return src.resize((size, size), Image.LANCZOS)


# --- packager icons (Cargo.toml [package.metadata.packager].icons) ---
render(32).save(ICONS / "32x32.png", optimize=True)
render(64).save(ICONS / "64x64.png", optimize=True)
render(128).save(ICONS / "128x128.png", optimize=True)
render(256).save(ICONS / "128x128@2x.png", optimize=True)
render(512).save(ICONS / "512x512.png", optimize=True)
render(512).save(ICONS / "icon.png", optimize=True)

# Windows exe icon (embedded via build.rs set_icon)
ico_base = render(256)
ico_base.save(
    ICONS / "icon.ico",
    format="ICO",
    sizes=[(16, 16), (24, 24), (32, 32), (48, 48), (64, 64), (128, 128), (256, 256)],
)

# macOS bundle icon (packager)
render(1024).save(
    ICONS / "icon.icns",
    format="ICNS",
    sizes=[(16, 16), (32, 32), (64, 64), (128, 128), (256, 256), (512, 512), (1024, 1024)],
)

# tray icon (include_bytes! in platform.rs) and store logo
render(64).save(ICONS / "tray.png", optimize=True)
render(50).save(ICONS / "StoreLogo.png", optimize=True)


def png_b64(img):
    buf = io.BytesIO()
    img.save(buf, format="PNG", optimize=True)
    return base64.b64encode(buf.getvalue()).decode()


def svg_wrap(img, path):
    b64 = png_b64(img)
    w, h = img.size
    svg = (
        f'<svg width="{w}" height="{h}" viewBox="0 0 {w} {h}" '
        f'xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">'
        f'<image width="{w}" height="{h}" xlink:href="data:image/png;base64,{b64}"/></svg>\n'
    )
    path.write_text(svg)
    print("wrote", path, f"({w}x{h}, {len(svg)//1024} KiB)")


# --- in-app startup logo (assets/logo.svg, rendered by SvgViewer) ---
svg_wrap(render(512), ASSETS / "logo.svg")

# --- macOS iconset layer (actool --app-icon TGLauncher) ---
iconset_assets = ICONS / "TGLauncher.icon" / "Assets"
old = iconset_assets / "oneclient-mark.svg"
if old.exists():
    old.unlink()
svg_wrap(render(1024), iconset_assets / "tglauncher-mark.svg")

icon_json = ICONS / "TGLauncher.icon" / "icon.json"
ij = icon_json.read_text()
ij = ij.replace('"image-name": "oneclient-mark.svg"', '"image-name": "tglauncher-mark.svg"')
ij = ij.replace('"name": "oneclient-mark"', '"name": "tglauncher-mark"')
ij = ij.replace('"scale": 3.6', '"scale": 1.0')
icon_json.write_text(ij)
print("updated icon.json")


# --- dmg background (600x400, @2x 1200x800, tiff) ---
def draw_dmg(scale):
    W, H = 600 * scale, 400 * scale
    img = Image.new("RGB", (W, H), (24, 25, 30))
    d = ImageDraw.Draw(img)

    def font(path, size):
        return ImageFont.truetype(path, size * scale)

    title = "Install TG Launcher"
    f_title = font(FONT_BOLD, 30)
    tw = d.textlength(title, font=f_title)
    d.text(((W - tw) / 2, 28 * scale), title, font=f_title, fill=(232, 233, 238))

    # left tile = the app icon, right tile = Applications folder
    tile = 150 * scale
    y0 = 120 * scale
    x_left = 82 * scale
    x_right = 368 * scale
    r = 24 * scale
    tile_fill = (216, 218, 228)
    d.rounded_rectangle([x_left, y0, x_left + tile, y0 + tile], radius=r, fill=tile_fill)
    d.rounded_rectangle([x_right, y0, x_right + tile, y0 + tile], radius=r, fill=tile_fill)

    # emblem inside the left tile with margin
    margin = 14 * scale
    emblem = render(int(tile - 2 * margin))
    # round the emblem corners to match the tile
    mask = Image.new("L", emblem.size, 0)
    ImageDraw.Draw(mask).rounded_rectangle([0, 0, emblem.size[0], emblem.size[1]], radius=int(r * 0.8), fill=255)
    img.paste(emblem, (int(x_left + margin), int(y0 + margin)), mask)

    # arrow
    ax0, ax1 = x_left + tile + 18 * scale, x_right - 18 * scale
    ay = y0 + tile // 2
    d.line([ax0, ay, ax1, ay], fill=(88, 101, 242), width=8 * scale)
    d.polygon([(ax1, ay), (ax1 - 16 * scale, ay - 9 * scale), (ax1 - 16 * scale, ay + 9 * scale)], fill=(88, 101, 242))

    caption = "Drag TG Launcher into the Applications folder"
    f_cap = font(FONT_REG, 15)
    cw = d.textlength(caption, font=f_cap)
    d.text(((W - cw) / 2, 330 * scale), caption, font=f_cap, fill=(168, 172, 184))
    return img


hi = draw_dmg(2)
hi.save(DIST / "dmg-background@2x.png", optimize=True)
lo = draw_dmg(1)
lo.save(DIST / "dmg-background.png", optimize=True)
lo.save(DIST / "dmg-background.tiff", format="TIFF")
print("dmg backgrounds written")

print("DONE")
