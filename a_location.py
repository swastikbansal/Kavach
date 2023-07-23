import android
import csv
import time

def get_and_save_location():
    droid = android.Android()
    location = droid.getLastKnownLocation().result
    if location:
        latitude = location['gps']['latitude']
        longitude = location['gps']['longitude']
        timestamp = time.strftime('%Y-%m-%d %H:%M:%S')


        # Print the fetched location
        print(f'Current Location: Latitude={latitude}, Longitude={longitude}')

        # Save the location to a CSV file
        """with open('location_log.csv', 'a', newline='') as csvfile:
            csv_writer = csv.writer(csvfile)
            csv_writer.writerow([timestamp, latitude, longitude])
        
        print('Location saved successfully.')
    else:
        print('Unable to get location.')"""

if __name__ == '__main__':
    get_and_save_location()