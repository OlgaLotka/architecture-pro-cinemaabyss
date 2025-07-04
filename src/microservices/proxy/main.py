import socket
import threading
import os
from dotenv import load_dotenv, dotenv_values 

dotenv_path = os.path.join(os.path.dirname(__file__), '.env')

def start_proxy_server(host, port):

    # Create a TCP socket
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    #server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    host1 = socket.gethostname()
    server_socket.bind((host, port))
    server_socket.listen(10)
    #server_socket.settimeout(5)
    print(f"Proxy server listening on {host}:{port}")

    client_socket, client_address = server_socket.accept()

    #with client_socket:
        
    while True:
        print(f"Connection from {client_address}")
        request = client_socket.recv(1024)
        print(f"Request received: {request}")
        if request == b'':
            break
        client_handler = threading.Thread(target=handle_client, args=(client_socket, request))
        client_handler.start()
        

def handle_client(client_socket, request):

    load_dotenv() 
    mono_url = os.getenv('MONOLITH_URL', "local")
    move_url = os.getenv('MOVIES_SERVICE_URL', "local")
    event_url = os.getenv('EVENTS_SERVICE_URL', "local")
    # Assuming request format is: METHOD URL HTTP/1.1
    request_lines = request.split(b'\n')
    #if request_lines[0] == b'': 
    #    return

    url = request_lines[0].split()[1]

    # Parse the URL to extract the host and port

    if url == b'/health': 
       movies = bytearray(b'/api/movies')
    else:
        movies = bytearray(b'/')
   
    http_pos = url.find(b':')
    if http_pos == -1:
        temp = movies + url
    else:
        temp = url[(http_pos+3):]

    port_pos = temp.find(b':')

    # Find end of web server
    webserver_pos = temp.find(b'/')
    if webserver_pos == -1:
        webserver_pos = len(temp)

    webserver = ""
    port = -1
    if (port_pos == -1 or webserver_pos < port_pos):
        if url.find(b'users'):
            target_service = mono_url[:(mono_url.find(':'))]
            port = int(mono_url[(mono_url.find(':')+1):])
        else:
            target_service =  event_url[:(event_url.find(':'))]
            port = int(event_url[(event_url.find(':')+1):])   
       
        webserver = bytearray(target_service.encode('utf-8')) + temp[:webserver_pos]
        print(f"trget = {target_service}") 
    else:
        port = int((temp[(port_pos+1):])[:webserver_pos-port_pos-1])
        webserver = temp[:port_pos]
    print(f"trget = {target_service}") 
    proxy_server(webserver, port, client_socket, request)

def proxy_server(webserver, port, client_socket, request):
    proxy_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    proxy_socket.connect((webserver, port))
    proxy_socket.send(request)

    while True:
        response = proxy_socket.recv(4096)
        if len(response) > 0:
            client_socket.send(response)
        else:
            break

    proxy_socket.close()
    client_socket.close()    


if __name__ == "__main__":
    load_dotenv() 
    print(f"target = {os.getenv('MONOLITH_URL', 'local')}")  
    start_proxy_server('0.0.0.0', 8000)