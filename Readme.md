# Code lab compiler

A short description of the project.

## Installation

## Usage

1- Build a docker image:
```shell
docker image build . -t compiler
```

2- Create a volume:
```shell
docker volume create compiler
```

3- build the necessary docker images used by the compiler
```shell
./environment/build.sh
```

4- Run the container:
```shell
   
```