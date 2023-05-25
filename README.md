[CareFer task]

[CareFer task] is an Android app that follows the Model-Repository-ViewModel-View (MVVM)
architecture pattern. It consists of four layers:

1- Model: This layer includes the data models that represent the data used in the app. The data can
be stored locally or retrieved from a remote server.

2- Repository: This layer acts as a bridge between the Model and ViewModel layers. It provides a
clean interface for the ViewModel to access the data in the Model layer.


3- ViewModel: This layer contains the business logic of the app. It communicates with the Repository
layer to get the data and provides it to the View layer.

4- View: This layer is responsible for displaying the data to the user. It communicates with the
ViewModel layer to get the data and updates the UI accordingly.



viewmodel have [performNetworkCall] method which responsible for calling apis and handle response 


for data base app using shared preference which responsible for saving some keys and values 
also using room data base to use fave teams 


<img src="https://res.cloudinary.com/dn2hcmcqn/image/upload/v1685058929/Screenshot_1685058895_pgiima.png" width="300">
<img src="https://res.cloudinary.com/dn2hcmcqn/image/upload/v1685058929/Screenshot_1685058895_pgiima.png" width="300">
