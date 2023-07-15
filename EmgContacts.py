import csv

header = ["Contacts","Mobile Number"]

#Creating a new empty CSV File and adding headers at first row
file = open("EmergencyContacts.csv","a+")
data = csv.reader(file) 

for row in data:
    csv.writer(file).writerow(header)
    print(data)
    print("Data : " , row)

    if  header != row:
        csv.writer(file).writerow(header)
        break

    else:
        break

while True:
    #Inputting emegency contacts data
    name = input("Enter the name of the contact : ")
    mobile_number = input("Enter the Contact Number : ")

    contacts_list = [name,mobile_number]
    
    if mobile_number.isnumeric():
        
        #Writing to CSV file
        with file:
            writer = csv.writer(file)
            writer.writerow(contacts_list)
        
        #Asking user if he wants to add more contacts or not 
        choice  = input("Do you want to add more contacts(y/n) : ")

        if choice.lower() == "y":
            continue

        else :
            break



    else :
        print('Invalid Mobile number')