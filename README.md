
#### Running alpine/socat container to forward traffic from Jenkins to Docker Desktop on Host Machine

Firstly execute this command on your machine to create alpine/socat container:

`docker run -d --restart=always -p 127.0.0.1:2376:2375 --network jenkins -v /var/run/docker.sock:/var/run/docker.sock alpine/socat tcp-listen:2375,fork,reuseaddr unix-connect:/var/run/docker.sock`

Then execute `docker inspect` with just created container. Copy IP Address from Networks of this container and paste it to "Docker Host URI" in Jenkins cloud agent config.



