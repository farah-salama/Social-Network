package com.example.social_network;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import java.util.HashMap;
import java.util.Map;

public class GraphViewController {
    @FXML
    public Pane graphPane;
    public SocialNetworkRepresentation socialNetwork;

    public void setSocialNetwork(SocialNetworkRepresentation socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public void initialize() {
        if (socialNetwork != null) {
            Map<User, Point2D> nodePositions = new HashMap<>();

            // Calculate positions for nodes "using circular layout"
            int numNodes = socialNetwork.getUsers().size();
            double angleIncrement = 2 * Math.PI / numNodes;
            double radius = 150.0;

            for (int i = 0; i < numNodes; i++) {
                double angle = i * angleIncrement;
                double x = 300 + radius * Math.cos(angle);
                double y = 200 + radius * Math.sin(angle);

                //store each user with the coordinates of its nodes in the map
                nodePositions.put(socialNetwork.getUsers().get(i), new Point2D(x, y));
            }

            // Create nodes and edges
            for (User user : socialNetwork.getUsers()) {
                double userX = nodePositions.get(user).getX();
                double userY = nodePositions.get(user).getY();

                // Add a circle for each user
                Circle userCircle = new Circle(userX, userY, 10);
                userCircle.setFill(Color.TRANSPARENT); // Set the fill color to transparent
                userCircle.setStroke(Color.DARKCYAN); // Set the outline color to black
                graphPane.getChildren().add(userCircle);

                // Add username as a label
                Text userName = new Text(userX, userY - 15, user.getName());
                graphPane.getChildren().add(userName);

                // Connect user with followers using lines with arrows
                for (User follower : socialNetwork.getAdjNodes(user)) {
                    double followerX = nodePositions.get(follower).getX();
                    double followerY = nodePositions.get(follower).getY();

                    // Adjust starting and ending points of the line
                    double lineStartX = userX + 10 * Math.cos(Math.atan2(followerY - userY, followerX - userX));
                    double lineStartY = userY + 10 * Math.sin(Math.atan2(followerY - userY, followerX - userX));
                    double lineEndX = followerX + 10 * Math.cos(Math.atan2(userY - followerY, userX - followerX));
                    double lineEndY = followerY + 10 * Math.sin(Math.atan2(userY - followerY, userX - followerX));


                    Line line;
                    Polygon arrowhead;
                    if (user.equals(follower)) { // If user and follower are the same
                        // Create a loop with an arrowhead
                        Arc loop = new Arc(userX, userY - 20, 20, 20, 0, 359);
                        loop.setFill(Color.TRANSPARENT);
                        loop.setStroke(Color.BLACK);
                        graphPane.getChildren().add(loop);


                        // Create an arrowhead for the loop
                        arrowhead = createArrowhead(userX, userY - 40, userX + 0.1, userY - 40);
                    } else {
                            line = new Line(lineStartX, lineStartY, lineEndX, lineEndY);
                            graphPane.getChildren().add(line);

                        // Add arrowhead
                            arrowhead = createArrowhead(lineStartX, lineStartY, lineEndX, lineEndY);

                    }
                    arrowhead.setFill(Color.BLACK); // Set the fill color to black
                    graphPane.getChildren().add(arrowhead);
                }
            }
        }
    }

    private Polygon createArrowhead(double startX, double startY, double endX, double endY) {
        //double arrowLength = 20; // Length of the arrowhead
        double arrowWidth = 7;  // Width of the arrowhead

        // Calculate the angle of the line
        double angle = Math.atan2((endY - startY), (endX - startX));

        // Create the arrowhead
        Polygon arrowhead = new Polygon();
        arrowhead.getPoints().addAll(new Double[]{
                endX, endY,
                endX - arrowWidth * Math.cos(angle - Math.PI / 6), endY - arrowWidth * Math.sin(angle - Math.PI / 6),
                endX - arrowWidth * Math.cos(angle + Math.PI / 6), endY - arrowWidth * Math.sin(angle + Math.PI / 6)
        });

        return arrowhead;
    }
}
