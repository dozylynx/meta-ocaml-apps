inherit image_types

# This image depends on the rootfs image
IMAGE_TYPEDEP_vhd-image = "${VHDIMG_ROOTFS_TYPE}"

# Use an uncompressed ext4 by default as rootfs
VHDIMG_ROOTFS_TYPE ?= "ext4"
VHDIMG_ROOTFS = "${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.${VHDIMG_ROOTFS_TYPE}"

# VHD image name
VHDIMG = "${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.${VHDIMG_ROOTFS_TYPE}.vhd"

IMAGE_CMD_vhd-image () {
    install -d ${DEPLOY_DIR_IMAGE}/tmp.vhd

    mke2fs -t ${VHDIMG_ROOTFS_TYPE} \
           -d ${IMAGE_ROOTFS} \
           ${DEPLOY_DIR_IMAGE}/tmp.vhd/${IMAGE_NAME}.rootfs.${VHDIMG_ROOTFS_TYPE} \
           ${ROOTFS_SIZE} \
           ${EXTRA_IMAGECMD}

    vhd-tool stream \
             --source-format=raw \
             --source="${DEPLOY_DIR_IMAGE}/tmp.vhd/${IMAGE_NAME}.rootfs.${VHDIMG_ROOTFS_TYPE}" \
             --source-protocol=none \
             --destination-format=vhd \
             --destination="file://${DEPLOY_DIR_IMAGE}/tmp.vhd/${IMAGE_NAME}.rootfs.${VHDIMG_ROOTFS_TYPE}.vhd"

    rm -f ${DEPLOY_DIR_IMAGE}/tmp.vhd/${IMAGE_NAME}.rootfs.${VHDIMG_ROOTFS_TYPE}

    mv ${DEPLOY_DIR_IMAGE}/tmp.vhd/${IMAGE_NAME}.rootfs.${VHDIMG_ROOTFS_TYPE}.vhd \
       ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.${VHDIMG_ROOTFS_TYPE}.vhd
}

do_image_vhd_image[depends] += " \
    vhd-tool-native:do_populate_sysroot \
    e2fsprogs-native:do_populate_sysroot \
    "
