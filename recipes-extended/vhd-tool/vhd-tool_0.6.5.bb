SUMMARY = "Command line tool for manipulating Virtual Hard Disk (VHD) image files"
HOMEPAGE = "https://github.com/xapi-project/vhd-tool"

# vhd-tool itself is licensed as: LGPL-2.1-with-OCaml-linking-exception
# and the other software licenses are introduced by its dependencies.
LICENSE = "LGPL-2.1-with-OCaml-linking-exception & BSD-3-Clause & LGPL-2.1 & MIT & ISC & Apache-2.0"
LIC_FILES_CHKSUM="file://LICENSE;md5=4c2a00ee014c6b0d77bc89bcb38eff71"

inherit opam

SRC_URI = " \
    opam://vhd-tool;switch=4.02.2;solution=vhd-tool.0.6.5.json;repos=opam-ocaml-org \
    file://nbd.0.9.2.rename-result.patch;applyto=nbd.0.9.2 \
    file://vhd-tool.0.6.5.rename-result.patch;applyto=vhd-tool.0.6.5 \
    file://sha.1.9.missing-includes.patch;applyto=sha.1.9 \
    file://ssl.0.4.7.cross-compile.patch;applyto=ssl.0.4.7 \
    file://camlp4.4.02+7.cross-compile.patch;applyto=camlp4.4.02+7 \
    file://cohttp.0.9.13.disable-tests.patch;applyto=cohttp.0.9.13 \
    file://tar-format.0.2.1.cross-compile.patch;applyto=tar-format.0.2.1 \
    file://vhd-tool.0.6.5.cross-compile.patch;applyto=vhd-tool.0.6.5 \
    file://fieldslib.113.00.00.cross-compile.patch;applyto=fieldslib.113.00.00 \
    file://sexplib.113.00.00.cross-compile.patch;applyto=sexplib.113.00.00 \
    file://lwt.2.4.5.cross-compile.patch;applyto=lwt.2.4.5 \
    file://lwt.2.4.5.disable-default-paths.patch;applyto=lwt.2.4.5 \
    file://ounit.2.0.0.disable-docs.patch;applyto=ounit.2.0.0 \
    file://type_conv.113.00.02.cross-compile.patch;applyto=type_conv.113.00.02 \
    file://cohttp.0.9.13.cross-compile.patch;applyto=cohttp.0.9.13 \
    file://cohttp.0.9.13.disable-docs.patch;applyto=cohttp.0.9.13 \
    file://tar-format.0.2.1.disable-test.patch;applyto=tar-format.0.2.1 \
    file://vhd-format.0.6.4.cross-compile.patch;applyto=vhd-format.0.6.4 \
    file://vhd-format.0.6.4.disable-tests.patch;applyto=vhd-format.0.6.4 \
    file://ocamlfind.1.7.3.cross-compile.patch;applyto=ocamlfind.1.7.3 \
    "

SRC_URI_append_class-target = " \
    file://obuild.0.1.8.cross-compile-using-native.patch;applyto=obuild.0.1.8 \
    file://lwt.2.4.5.set-library-path.patch;applyto=lwt.2.4.5 \
    "

DEPENDS = " \
    openssl \
    time-native \
    pkgconfig-native \
    "

DEPENDS_append_class-target = " vhd-tool-cross-deps"

S = "${WORKDIR}"

BBCLASSEXTEND = "native"

do_compile_prepend_class-target() {
    # For non-native ssl build
    export LIBDIRS="${STAGING_LIBDIR} ${STAGING_BASELIBDIR}"
    # For lwt to find pthread
    export LIBRARY_PATH="${STAGING_LIBDIR}"

    # Necessary for cross-compile of camlp4: directs build to use
    # the available native camlp4boot tool (built native in deps package).
    export EXT_CAMLP4BOOT="1"
}

FILESEXTRAPATHS_prepend := "${THISDIR}/vhd-tool:"
