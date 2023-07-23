import csv
from geopy.geocoders import Nominatim
import datetime

def get_location():
    # Initialize the geolocator
    geolocator = Nominatim(user_agent="phone_gps_app")

    # Get the latitude and longitude of the device
    location = geolocator.geocode("")
    if location is None:
        raise ValueError("Unable to retrieve location information.")
    
    latitude = location.latitude
    longitude = location.longitude

    return latitude, longitude

def save_location_to_csv(latitude, longitude):
    # Get current date and time
    now = datetime.datetime.now()

    # Open the CSV file in append mode and write the data
    with open("phone_location.csv", "a", newline="") as file:
        writer = csv.writer(file)
        writer.writerow([now, latitude, longitude])

if __name__ == "__main__":
    try:
        latitude, longitude = get_location()
        save_location_to_csv(latitude, longitude)
        print("Location saved to CSV successfully.")
    except Exception as e:
        print("Error occurred while getting or saving location:", e)
