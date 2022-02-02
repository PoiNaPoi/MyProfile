#include "mpi.h"
#include <stdio.h>
#include <stdlib.h>
#define STB_IMAGE_IMPLEMENTATION
#include "lib/stb_image.h"
#define STB_IMAGE_WRITE_IMPLEMENTATION
#include "lib/stb_image_write.h"

struct Image {
    int width;
    int height;
    int channels;
    size_t size;
};

int main(int argc, char *argv[]) {
    struct Image image;
    unsigned char *img = stbi_load("img/image.jpg", &image.width, &image.height, &image.channels, 0);
    image.size = image.width * image.height * image.channels;
    if(img == NULL) {
        printf("Error in loading the image\n");
        exit(1);
    }
    unsigned char *sepia_img_1 = malloc(image.size);
    unsigned char *sepia_img_2 = malloc(image.size);
    unsigned char *result = malloc(image.size);

    int rank, size;
    MPI_Status status;
    int interval, chank;
    int start, end;
    int i = 0;

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    interval = (image.width*image.height) / (size);
    start = (rank) * interval;
    chank = image.size/(size);

    MPI_Scatter(img, chank, MPI_UNSIGNED_CHAR, sepia_img_1, chank, MPI_UNSIGNED_CHAR, 0, MPI_COMM_WORLD);

    for(unsigned char *p = sepia_img_1, *pg = sepia_img_2; i < interval; p += image.channels, pg += image.channels, start++, i++) {
        *pg       = (uint8_t)(255.0 - *p);      // red
        *(pg + 1) = (uint8_t)(255.0 - *(p+1));  // green
        *(pg + 2) = (uint8_t)(255.0 - *(p+2));  // blue
        if(image.channels == 4) {
            *(pg + 3) = *(p + 3);
        }
    }
    MPI_Gather(sepia_img_2, chank, MPI_UNSIGNED_CHAR, img, chank, MPI_UNSIGNED_CHAR, 0,MPI_COMM_WORLD);
    if(rank==0)
        stbi_write_jpg("image_invert_Ans.jpg", image.width, image.height, image.channels, img, 100);
    MPI_Finalize();
}