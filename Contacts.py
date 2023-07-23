import csv
from itertools import cycle

# CSV File Class
class Contacts:
    def __init__(self):
        # Creating a new empty CSV File and adding headers at first row
        self.file = open("EmergencyContacts.csv", "a+")

    # Writing Data into CSV File
    def writeFile(self, name, number):
        self.writeData = [name, number]

        # Writing data
        with self.file:
            writer = csv.writer(self.file)
            writer.writerow(self.writeData)

    # Function for reading data from file
    def readFile(self):
        self.file.seek(0)
        self.readData = csv.reader(self.file)
        self.data_list = []

        for rows in self.readData:
            self.data_list.append(rows)

        return self.data_list

    #Function for deleting contacts from csv file
    def deleteFile(self,deleteData):
        orgData = self.readFile()

        #Using cycle function to iterate through objects
        data = cycle(deleteData)

        for row in orgData:
            nextData = next(data) 
            if nextData in orgData:
                orgData.remove(nextData)
        
        while True:
            #Removing Contacts
            if [] in orgData:
                orgData.remove([])
            
            #Deleting empty Lists
            if [] not in orgData:
                break
        
        with open("EmergencyContacts.csv", "w") as file : 
            writer = csv.writer(file)
            writer.writerows(orgData)






    # if mobileNumber.isnumeric():
    #     # Writing to CSV file
    #     with CSVFileObject.file:
    #         CSVFileObject.writeFile(contactsList)
    #         print("Data Updated Successfully !")

    #         print("Current Contacts in File\n", CSVFileObject.readFile())

    #         # Asking user if he wants to add more contacts or not
    #         choice = input("Do you want to add more contacts(y/n) : ")

    #         if choice.lower() == "y":
    #             continue

    #         else:
    #             break

    # else:
    #     print("Invalid Mobile number")
