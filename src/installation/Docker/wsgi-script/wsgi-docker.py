import os
os.execl('docker', 'run', 'run', '-d', '-it', '-p', '8002:80', '--name', 'apache', '-t', 'ubuntu/iedcs')
os.execl('docker', 'start', 'apache')
os.execl('docker', 'exec', 'apache', 'service', 'apache2', 'start')

from docker import Client
cli = Client(base_url='unix://var/run/docker.sock', version='1.18')
cli.create_container(image="ubuntu/iedcs", name="apache", detach=True, ports=[8002, 80], tty=True, stdin_open=True)
cli.start(container="apache")
cli.exec_create(container="apache", cmd="service apache2 start")
