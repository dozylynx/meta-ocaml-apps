#!/bin/bash

# Nativize:
#export CAML_LD_LIBRARY_PATH=\$(echo \$CAML_LD_LIBRARY_PATH | \
#                              sed 's,/opam-root/cx-,/opam-root/,g')

#export OCAMLFIND_LDCONF="${OPAMROOT}/${SWITCH}/lib/ocaml/ld.conf"
#export OCAMLFIND_CONF="${OPAMROOT}/${SWITCH}/lib/jbuilder-host-findlib.conf"
#export OCAMLLIB="${OPAMROOT}/${SWITCH}/lib/ocaml"
#export OPAM_SWITCH_PREFIX="${OPAMROOT}/${SWITCH}"

for ARG in "$@" ; do
    if [ "$ARG" = "build" ] ; then
        #CROSS_ARGS="-x cross"
        export OCAMLFIND_LDCONF="${OPAMROOT}/${OPAMSWITCH}/lib/ocaml/ld.conf"
        export OCAMLFIND_CONF="${OPAMROOT}/${OPAMSWITCH}/lib/findlib.conf"
        export OCAMLLIB="${OPAMROOT}/${OPAMSWITCH}/lib/ocaml"
        export OPAM_SWITCH_PREFIX="${OPAMROOT}/${OPAMSWITCH}"

        break
    fi
done

NATIVE_SWITCH="$(echo ${OPAMSWITCH} | cut -c 4-)"
exec "${OPAMROOT}/${NATIVE_SWITCH}/bin/jbuilder" "$@" ${CROSS_ARGS}
