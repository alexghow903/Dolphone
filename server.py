import socket

HOST=socket.gethostbyname(socket.gethostname())
PORT= 8080

print(HOST)

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.bind((HOST, PORT))
    s.listen()
    conn, addr = s.accept()
    with conn:
        print(f"Connected by {addr}")
        while True:
            data = conn.recv(addr[1])
            if not data:
                break
            conn.sendall(data)
