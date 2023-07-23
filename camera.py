import cv2
import speech_recognition as sr

# Function to recognize speech using Google Web Speech API
def recognize_speech():
    recognizer = sr.Recognizer()
    with sr.Microphone() as source:
        print("Listening for trigger word...")
        audio = recognizer.listen(source)

    try:
        result = recognizer.recognize_google(audio).lower()
        print("You said:", result)
        return result
    except sr.UnknownValueError:
        print("Google Web Speech API could not understand the audio.")
        return None
    except sr.RequestError as e:
        print("Could not request results from Google Web Speech API; {0}".format(e))
        return None

# Function to capture an image using the camera
    

def capture_image(camera_id):
    # Open the camera
    cap = cv2.VideoCapture(camera_id)

    # Check if the camera is opened successfully
    if not cap.isOpened():
        print("Error: Unable to access the camera")
        return None

    # Read a frame from the camera
    ret, frame = cap.read()

    # Release the camera
    cap.release()

    # If the frame was read successfully, save it to a file
    if ret:
        image_path = "captured_image.jpg"
        cv2.imwrite(image_path, frame)
        print("Image captured and saved as captured_image.jpg")
        return image_path
    else:
        print("Error: Image capture failed")
        return None

# Camera ID (use 0 for the default camera)
camera_id = 0

# Capture an image from the camera
captured_image_path = capture_image(camera_id)

# Now you can use the captured_image_path for further processing or display the image, etc
trigger_word = "help"


# Main loop to continuously listen for the trigger word and capture images
while True:
    result = recognize_speech()
    if result and trigger_word in result:
        print("Trigger word detected! Capturing image...")
        # Capture an image from the camera
        image = capture_image(camera_id)
        if image is not None:
            # Save the captured image to a file (you can also process the image here)
            cv2.imwrite("captured_image.jpg", image)
            print("Image captured and saved as captured_image.jpg")
        else:
            print("Error: Image capture failed")
