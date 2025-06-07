#!/bin/bash
docker run -it --rm -v "$(pwd)/certificados:/workdir" plass/mdtopdf mdtopdf "$1"
