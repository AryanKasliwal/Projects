import socket


# Function for the command "POST"
def post(socket, action):
    sendText = action                                        # sendText: accumulated text to send to server
    sendText += '\n'

    text = input()                                           # text: user inputs a text that gets added to sendText
    print(f"Client: {text}")

    sendText += text
    sendText += '\n'
    while text != ".":                                       # Ensure user enters text until they enter a "."
        text = input()
        print(f"Client: {text}")

        sendText += text
        sendText += '\n'

    s.send(bytes(sendText, "utf-8"))                         # Send sendText to the server
    print(f"Server: {socket.recv(1024).decode('utf-8')}")    # Receive "OK" from the server


# Function for command "READ"
def read(socket, action):
    socket.send(bytes(action, "utf-8"))                      # Send "READ" to the server

    msg = socket.recv(1024).decode("utf-8")                  # Receive the sent messages from the server
    print(f"Server: {msg}")
    while msg != ".\x00":                                    # Receive messages from the server until "." is received
        msg = socket.recv(1024).decode("utf-8")
        print(f"Server: {msg}")


# Function for unidentified command
def error(socket, action):
    socket.send(bytes(action, "utf-8"))                           # Send the unidentified command to server
    print(f"Server: {s.recv(1024).decode('utf-8')}")         # Receive the Error message from server


# Function for command "QUIT"
def quit(socket, action):
    socket.send(bytes(action + '\n', "utf-8"))              # Send "QUIT" to the server
    print(f"Server: {socket.recv(1024).decode('utf-8')}")   # Receive "OK" from the server
    socket.close()                                          # Close the socket once "QUIT" is sent and "OK" is received.


if __name__ == "__main__":
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    serverIP = input("Input the IP address:")                   # Take user input for serverIP

    # Try except avoids errors in case the program does not connect to the server
    try:
        s.connect((serverIP, 16000))                            # Connect to the specified IP and port
        print("IP Address: " + serverIP + '\t' + "Port Number: 16000")
        print("Status: Connected")
        command = input("Enter a command for the server: ")     # Read command from the user

        while command != "QUIT":                                # Read commands until command is "QUIT".
            if command == "POST":
                post(s, command)
            elif command == "READ":
                read(s, command)
            else:
                error(s, command)
            command = input("Enter a command for the server: ")  # Read command until command is "QUIT".
        quit(s, command)

    except:
        print(f"Could not connect to {serverIP}")               # In case the connection fails.
