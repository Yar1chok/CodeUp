import subprocess

command = "mvn spring-boot:run"
subprocess.run(command, shell=True, check=True)
