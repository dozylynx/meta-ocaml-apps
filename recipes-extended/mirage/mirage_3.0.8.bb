SUMMARY = "Mirage OS"
HOMEPAGE = "https://mirage.io"

LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM="file://LICENSE.md;md5=aa76e1df60e6a140b4061c6d33a6871a"

inherit opam

SRC_URI = " \
    opam://mirage;switch=4.04.2;repos=opam-ocaml-org;solution=mirage.3.0.8.mirage.4.04.2.solution.json \
    file://ocamlfind.1.8.0.cross-compile.patch;applyto=ocamlfind.1.8.0 \
    file://ocamlbuild.0.12.0.cross-compile.patch;applyto=ocamlbuild.0.12.0 \
    file://base.v0.11.0.cross-compile.patch;applyto=base.v0.11.0 \
    file://ocaml-compiler-libs.v0.11.0.cross-compile.patch;applyto=ocaml-compiler-libs.v0.11.0 \
    file://ocaml-migrate-parsetree.1.0.10.cross-compile.patch;applyto=ocaml-migrate-parsetree.1.0.10 \
    file://ppxlib.0.2.0.cross-compile.patch;applyto=ppxlib.0.2.0 \
    file://jbuilder.1.0+beta20.ocamlfind-libraries.patch;applyto=jbuilder.1.0+beta20 \
    "

SRC_URI_append_class-target = " \
    file://jbuilder.1.0+beta20.cross-compile.patch;applyto=jbuilder.1.0+beta20 \
    "

DEPENDS = " \
    pkgconfig-native \
    "

DEPENDS_append_class-target = " mirage-cross-deps"

S = "${WORKDIR}"

BBCLASSEXTEND = "native"

FILES_${PN}-doc += "/doc"
FILES_${PN}-staticdev += "${base_libdir}"

INSANE_SKIP_${PN} = "ldflags"
INSANE_SKIP_${PN}-staticdev = "ldflags file-rdeps"

FILESEXTRAPATHS_prepend := "${THISDIR}/mirage:"
