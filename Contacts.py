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
            self.file.close()

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
            self.file.close()
        
        #Function for updating contacts from csv file
    def updateFile(self,oldData,updatedData):
        orgData = self.readFile()

        print(orgData)

        #Using cycle function to iterate through objects
        iterOldData = cycle(oldData)
        iterUpdateData = cycle(updatedData)


        for row in orgData:
            nextOldData = next(iterOldData) 
            nextUpdateData = next(iterUpdateData)

            if nextOldData in orgData:
                orgData.remove(nextOldData)
                orgData.append(nextUpdateData)

        
        while True:
            #Removing Contacts
            if [] in orgData:
                orgData.remove([])
            
            #Deleting empty Lists
            if [] not in orgData:
                break
        
        print(orgData)

        with open("EmergencyContacts.csv", "w") as file : 
            writer = csv.writer(file)
            writer.writerows(orgData)
            self.file.close()
