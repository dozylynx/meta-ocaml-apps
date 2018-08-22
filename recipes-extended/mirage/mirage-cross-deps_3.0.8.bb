SUMMARY = "Build environment dependencies for mirage recipe"
HOMEPAGE = "https://mirage.io"

LICENSE = "LGPL-2.1-with-OCaml-linking-exception"
LIC_FILES_CHKSUM="file://LICENSE;md5=5123b1988300c0d24c79e04f09d86dc0"

inherit ocaml opam cross

BBCLASSEXTEND="opam32-native"

# FIXME: cut down this SRC_URI

SRC_URI = " \
    opam://ocamlbuild;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://ocamlfind;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://jbuilder;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://cmdliner;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://result;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://rresult;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://parsexp;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://fmt;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://ocamlgraph;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://astring;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://fpath;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://bos;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://base;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://ocaml-migrate-parsetree;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://ocaml-compiler-libs;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://sexplib;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://sexplib0;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://stdio;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://ppx_derivers;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://ppxlib;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://ppx_sexp_conv;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://functoria-runtime;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://functoria;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://ipaddr;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    opam://mirage-runtime;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    file://ppxlib.0.2.0.cross-compile.patch;applyto=ppxlib.0.2.0 \
    file://jbuilder.1.0+beta20.ocamlfind-libraries.patch;applyto=jbuilder.1.0+beta20 \
    file://ocamlbuild.0.12.0.cross-compile.patch;applyto=ocamlbuild.0.12.0 \
    file://ocamlfind.1.8.0.cross-compile.patch;applyto=ocamlfind.1.8.0 \
    file://base.v0.11.0.cross-compile.patch;applyto=base.v0.11.0 \
    file://ocaml-compiler-libs.v0.11.0.cross-compile.patch;applyto=ocaml-compiler-libs.v0.11.0 \
    file://ocaml-migrate-parsetree.1.0.10.cross-compile.patch;applyto=ocaml-migrate-parsetree.1.0.10 \
    file://opam-cx-jbuilder-wrapper.sh \
    "

DEPENDS = " \
    time-native \
    pkgconfig-native \
    "
# FIXME: opam-switch-4.04.2-opam-ocaml-org-ocamlbuild-native etc
#        should be made to be automatically pulled in by the switch.

S = "${WORKDIR}"

# FIXME: this wrapper belongs in a separate jbuilder support recipe.
# FIXME: should make it switch-agnostic

do_install_append_class-cross() {
    SWITCH="4.04.2"
    mkdir -p "${D}${base_prefix}/usr/share/ocaml/opam-root/cx-${SWITCH}/cx/bin"

    cp "${WORKDIR}/opam-cx-jbuilder-wrapper.sh" \
       "${D}${base_prefix}/usr/share/ocaml/opam-root/cx-${SWITCH}/cx/bin/jbuilder"
    chmod 755 "${D}${base_prefix}/usr/share/ocaml/opam-root/cx-${SWITCH}/cx/bin/jbuilder"
}

FILESEXTRAPATHS_prepend := "${THISDIR}/mirage:${THISDIR}/opam-cx-jbuilder-wrapper:"
