import csv

# CSV File Class
class Conacts:
    def __init__(self):
        # Creating a new empty CSV File and adding headers at first row
        self.file = open("EmergencyContacts.csv", "a+")

    # Writing Data into CSV File
    def writeFile(self,name,number):
        
        self.writeData = [name,number]  

        #Writing data
        with self.file :
            writer = csv.writer(self.file)
            writer.writerow(self.writeData)

    # Function for reading data from file
    def readFile(self):
        self.file.seek(0)
        self.readData = csv.reader(self.file)
        self.data_list = []

        for rows in self.readData:
            self.data_list.append(rows)

        print(self.data_list)

        return self.data_list






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
