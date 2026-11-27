{
  description = "TG Launcher pour NixOS";

  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";

  outputs = { self, nixpkgs }:
    let
      system = "x86_64-linux";
      pkgs = import nixpkgs {
        inherit system;
        config.allowUnfree = true;
      };

      tglauncher = pkgs.callPackage ./nix/package.nix { };
    in
    {
      packages.${system} = {
        inherit tglauncher;
        default = tglauncher;
      };

      apps.${system}.default = {
        type = "app";
        program = "${tglauncher}/bin/tglauncher";
      };

      overlays.default = final: prev: {
        tglauncher = final.callPackage ./nix/package.nix { };
      };
    };
}
