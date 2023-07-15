import csv



def writeFile(writeData,file):
        writer = csv.writer(file)
        writer.writerow(writeData)
        

#Creating a new empty CSV File and adding headers at first row
file = open("EmergencyContacts.csv","a+")
data = csv.reader(file) 

while True:
    #Inputting emegency contacts data
    name = input("Enter the name of the contact : ")
    mobileNumber = input("Enter the Contact Number : ")

    contactsList = [name,mobileNumber]
        
    if mobileNumber.isnumeric():
        #Writing to CSV file
        with file: 
            writeFile(contactsList)
            
            #Asking user if he wants to add more contacts or not 
            choice  = input("Do you want to add more contacts(y/n) : ")

            if choice.lower() == "y":
                continue

            else :
                break

    else :
        print('Invalid Mobile number')
    