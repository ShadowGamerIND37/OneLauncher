{ lib
, appimageTools
, fetchurl
, makeDesktopItem
, makeWrapper
}:

let
  pname = "tglauncher";
  version = "2.13.0";

  src = fetchurl {
    url = "https://github.com/ShadowGamerIND37/OneLauncher/releases/download/tglauncher-${version}/TGLauncher_${version}_linux_x86_64.AppImage";
    hash = "sha256-VtrkL2YNmA6KjHJAUX7m0HT0o/3vWOGh84+nOBYYdp4=";
  };

  extracted = appimageTools.extract { inherit pname version src; };

  desktopItem = makeDesktopItem {
    name = pname;
    desktopName = "TG Launcher";
    comment = "Minecraft launcher by TG";
    exec = "${pname} %U";
    icon = pname;
    terminal = false;
    categories = [ "Game" ];
    mimeTypes = [ "x-scheme-handler/tglauncher" ];
  };
in
appimageTools.wrapType2 {
  inherit pname version src;

  nativeBuildInputs = [ makeWrapper ];

  extraPkgs = pkgs: with pkgs; [
    libgpg-error
    libgcrypt
    vulkan-loader
    libxkbcommon
    wayland
    jdk8
    jdk17
    jdk21
    libGL
    openal
    libpulseaudio
    libxcursor
    libxrandr
    libxxf86vm
  ];

  extraInstallCommands = ''
    wrapProgram $out/bin/${pname} --unset GIO_EXTRA_MODULES

    install -Dm444 ${desktopItem}/share/applications/*.desktop \
      -t $out/share/applications

    find ${extracted} -maxdepth 2 -name '*.png' | head -n1 | while read -r icon; do
      install -Dm444 "$icon" $out/share/pixmaps/${pname}.png
    done
  '';

  meta = {
    description = "TG Launcher, Minecraft launcher by TG";
    homepage = "https://github.com/ShadowGamerIND37/OneLauncher";
    sourceProvenance = [ lib.sourceTypes.binaryNativeCode ];
    platforms = [ "x86_64-linux" ];
    mainProgram = pname;
  };
}
