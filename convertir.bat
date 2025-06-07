@echo off
docker run -it --rm -v %cd%\certificados:/workdir plass/mdtopdf mdtopdf ganador_%1.md
