# Code lab compiler

A short description of the project.

## Table of contents

- [Installation](#installation)
- [Usage](#usage)
- [Examples](#examples)
- [Documentation](#documentation)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

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
docker container run -p 8080:8082 --name remote-complier -v /var/run/docker.sock:/var/run/docker.sock -v compiler:/compiler -e DELETE_DOCKER_IMAGE=true -e EXECUTION_MEMORY_MAX=10000 -e EXECUTION_MEMORY_MIN=0 -e EXECUTION_TIME_MAX=15 -e EXECUTION_TIME_MIN=0 -e MAX_REQUESTS=1000 -e MAX_EXECUTION_CPUS=0.2 -e COMPILATION_CONTAINER_VOLUME=compiler -t compiler
```