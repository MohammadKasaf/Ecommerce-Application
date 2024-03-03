package org.example.Ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class UserInterface {

    private GridPane loginPage;
    Customer loggedInCustomer;
    HBox headerBar;
    HBox footerBar;
    Label welcomeLabel;

    ProductList productList=new ProductList();
    VBox productPage;
    VBox body;
    Button signInButton;
    Button placeOrderButton=new Button("place order");



    ObservableList itemsInCart= FXCollections.observableArrayList();



    public BorderPane createContent() {
        BorderPane root = new BorderPane();
        root.setPrefSize(600, 600);

        // Add loginPage to the center of the root
        root.setTop(headerBar);
        body=new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);
        productPage=productList.getAllProducts();
        body.getChildren().add(productPage);

        root.setBottom(footerBar);

        return root;
    }

    public UserInterface() {

        createLoginPage();
        createHeaderBar();
        createFooterBar();
    }

    // Create login page
    private void createLoginPage() {

        Text userNameText = new Text("Username");
        Text passwordText = new Text("Password");

        TextField userName= new TextField();
        PasswordField password = new PasswordField();

        userName.setPromptText("Enter your username");
        password.setPromptText("Enter your password");
        Label messageLabel = new Label("hii");
        Button loginButton=new Button("login");

        // Initialize loginPage and add controls to it
        loginPage = new GridPane();
        // loginPage.setStyle("-fx-background-color: gray;");
        loginPage.setVgap(10);
        loginPage.setHgap(10);
        loginPage.setAlignment(Pos.CENTER);// to put at center

        // Add controls to the loginPage
        loginPage.add(userNameText, 0, 1);
        loginPage.add(userName, 1, 1);
        loginPage.add(passwordText, 0, 2);
        loginPage.add(password, 1, 2);
        loginPage.add(messageLabel,0,3);
        loginPage.add(loginButton,1,3);


        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {



                String name=userName.getText();
                String pass=password.getText();
                Login login=new Login();
                loggedInCustomer=login.customerLogin(name,pass);
                if(loggedInCustomer!=null){

                    messageLabel.setText("welcome : "+ loggedInCustomer.getName());
                    welcomeLabel.setText("welcome : " + loggedInCustomer.getName());
                    headerBar.getChildren().add(welcomeLabel);
                    body.getChildren().clear();
                    body.getChildren().addAll(productPage,footerBar);


                }
                else{
                    messageLabel.setText("login failed please enter correct details");
                }


            }
        });
    }




    //create header bar and put at top
    private void createHeaderBar(){

        Button homeButton=new Button();
        Image image = new Image("file:///D:\\E-Commerece\\src\\main\\resources\\org\\example\\Ecommerce\\e.jpg");
        ImageView imageView=new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(80);
        homeButton.setGraphic(imageView);

        TextField searchBar=new TextField();
        searchBar.setPrefWidth(280);
        searchBar.setPromptText("search here");

        Button searchButton=new Button("Search");
        Button cartButton=new Button("Cart");
        signInButton=new Button("Sign in");
        welcomeLabel=new Label();
        headerBar=new HBox(20);
        headerBar.setPadding(new Insets(10));
        headerBar.setSpacing(10);
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(homeButton,searchBar,searchButton,signInButton,cartButton);
        headerBar.setBackground(Background.fill(Color.GRAY));





        // Add event when search button is clicked



        //add event when sign in button click
        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(loginPage);
                headerBar.getChildren().remove(signInButton);
            }
        });

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if(itemsInCart.size()==0){
                    showDialogue("Cart is empty please add some product in cart");
                }
                else {
                    body.getChildren().clear();
                    VBox prodPage = productList.getProductsInCart(itemsInCart);
                    prodPage.setAlignment(Pos.CENTER);
                    prodPage.setSpacing(10);
                    prodPage.getChildren().add(placeOrderButton);
                    body.getChildren().add(prodPage);
                    footerBar.setVisible(false);

                }
            }
        });


        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if (itemsInCart== null) {
                    showDialogue("Please add some product into cart to place order");
                } else if (loggedInCustomer == null) {
                    showDialogue("Please login first to place order");
                } else {
                    int  count = Order.placeMultipleOrder(loggedInCustomer,itemsInCart);
                    if (count!=0) {
                        showDialogue("The order of "+ count + " product was placed successfully!!");
                    } else {
                        showDialogue("Order failed");
                    }
                }
            }
        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(productPage);
                footerBar.setVisible(true);
                if(loggedInCustomer==null && headerBar.getChildren().indexOf(signInButton)==-1){
                    headerBar.getChildren().add(signInButton);
                }
            }
        });


    }

    //create buy button and put at bottom
    private void createFooterBar(){

        Button buyNowButton=new Button("Buy Now");
        Button addToCartButton=new Button("Add to cart");

        footerBar=new HBox();

        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton,addToCartButton);


        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if (product == null) {
                    showDialogue("Please select a product to place order");
                } else if (loggedInCustomer == null) {
                    showDialogue("Please login first to place order");
                } else {
                    boolean status = Order.placeOrder(loggedInCustomer, product);
                    if (status) {
                        showDialogue("The order was placed successfully!!");
                    } else {
                        showDialogue("Order failed");
                    }
                }
            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if(loggedInCustomer==null){
                    showDialogue("please login first to add any product into cart!!");
                }
                else {

                    Product product = productList.getSelectedProduct();
                    if (product == null) {
                        showDialogue("Please select a product to add it to cart");
                    }
                    itemsInCart.add(product);
                    showDialogue("selected item has been added to the cart");

                }
            }
        });
    }



    public void showDialogue(String message){

        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("MESSAGE");
        alert.showAndWait();
    }

}