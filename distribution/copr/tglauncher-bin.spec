%global debug_package %{nil}
%global __strip /bin/true
%global _appname TGLauncher
%global _rpmfile %{_appname}_%{version}_linux_x86_64.rpm

Name:           tglauncher-bin
Version:        2.0.1
Release:        1%{?dist}
Summary:        Next-generation open source Minecraft launcher (prebuilt)

License:        GPL-3.0-only
URL:            https://github.com/ShadowGamerIND37/OneLauncher
Source0:        %{url}/releases/download/tglauncher-%{version}/%{_rpmfile}

ExclusiveArch:  x86_64
BuildRequires:  bsdtar
Requires:       libxdo
Requires:       dbus-libs
Provides:       tglauncher = %{version}-%{release}

%description
TG Launcher is a next-generation open source Minecraft launcher.
This package ships the prebuilt binaries from the upstream release.

%prep
%setup -q -c -T
# Repackages the upstream release rpm; bsdtar reads its cpio payload directly.
bsdtar -xf %{SOURCE0}

%install
mkdir -p %{buildroot}
cp -a usr %{buildroot}/
ln -s oneclient_app %{buildroot}%{_bindir}/tglauncher

%files
%{_bindir}/oneclient_app
%{_bindir}/tglauncher
%{_datadir}/applications/*.desktop
%{_datadir}/icons/hicolor/*/apps/*

%changelog
* Wed Sep 09 2026 TG - 2.0.1-1
- See upstream release notes.
