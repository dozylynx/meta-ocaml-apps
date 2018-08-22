SUMMARY = "Build environment dependencies for vhd-tool recipe"
HOMEPAGE = "https://github.com/xapi-project/vhd-tool"

# vhd-tool itself is licensed as: LGPL-2.1-with-OCaml-linking-exception
# and the other software licenses are introduced by its dependencies.
LICENSE = "LGPL-2.1-with-OCaml-linking-exception & BSD-3-Clause & LGPL-2.1 & MIT & ISC & Apache-2.0"
LIC_FILES_CHKSUM="file://LICENSE;md5=77f085d1023152a31ada8288ffd6e8f1"

inherit ocaml opam cross

SRC_URI = " \
    opam://camlp4;switch=4.02.2;solution=vhd-tool.0.6.5.json;repos=opam-ocaml-org \
    opam://ocamlbuild;switch=4.02.2;solution=vhd-tool.0.6.5.json;repos=opam-ocaml-org \
    opam://ocamlfind;switch=4.02.2;solution=vhd-tool.0.6.5.json;repos=opam-ocaml-org \
    opam://type_conv;switch=4.02.2;solution=vhd-tool.0.6.5.json;repos=opam-ocaml-org \
    opam://cppo;switch=4.02.2;solution=vhd-tool.0.6.5.json;repos=opam-ocaml-org \
    opam://obuild;switch=4.02.2;solution=vhd-tool.0.6.5.json;repos=opam-ocaml-org \
    file://camlp4.4.02+7.cross-compile.patch;applyto=camlp4.4.02+7 \
    "

DEPENDS = " \
    time-native \
    pkgconfig-native \
    "

S = "${WORKDIR}"

# All of the camlp4 tools need a wrapper that sets CAML_LD_LIBRARY_PATH
# before invoking the binary, to function correctly in the cross switch.

do_install_append_class-cross() {
    SWITCH="4.02.2"
    mkdir -p "${D}${base_prefix}/usr/share/ocaml/opam-root/cx-${SWITCH}/cx/bin"
    for F in "${D}${base_prefix}/usr/share/ocaml/opam-root/${SWITCH}/bin/"*p4* \
             "${D}${base_prefix}/usr/share/ocaml/opam-root/${SWITCH}/bin/obuild" ; do
        TOOL="$(basename $F)"

        cat >"${D}${base_prefix}/usr/share/ocaml/opam-root/cx-${SWITCH}/cx/bin/$TOOL" <<EOF
#!/bin/bash

export CAML_LD_LIBRARY_PATH=\$(echo \$CAML_LD_LIBRARY_PATH | \
                              sed 's,/opam-root/cx-,/opam-root/,g')

exec "${OPAMROOT}/${SWITCH}/bin/$TOOL" "\$@"
EOF
        chmod 755 "${D}${base_prefix}/usr/share/ocaml/opam-root/cx-${SWITCH}/cx/bin/$TOOL"
    done
}

FILESEXTRAPATHS_prepend := "${THISDIR}/vhd-tool:"
