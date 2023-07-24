import geocoder

def get_and_print_location():
    g = geocoder.ip('me')  # Use 'me' to get the location of the current device's IP address

    if g.ok:
        latitude = g.lat
        longitude = g.lng
        address = g.address

        print(f"Current Location: Latitude={latitude}, Longitude={longitude}")
        print(f"Address: {address}")
    else:
        print("Unable to get location.")

if __name__ == '__main__':
    get_and_print_location()
