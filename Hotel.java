//I worked on the homework assignment alone, using only course materials.
import java.util.Scanner;
/**
 * This class manages guest check in, calculates payment for each day, and manages guest check out after the last
 * payment.
 *
 * @author Daphne Huang
 * @version 1.0
 */

public class Hotel {

    /**
     * Main method.
     *
     * @param args arguments from user input
     */
    public static void main(String[] args) {
        int totalFloors = Integer.parseInt(args[0]);
        int roomsPerFloor = Integer.parseInt(args[1]);
        if (totalFloors < 1 || roomsPerFloor < 1) {
            System.out.println("Invalid number of floors or rooms.");
        } else {
            int[][] roomCost = new int[totalFloors][roomsPerFloor];
            Scanner input = new Scanner(System.in);
            int i = 1;
            while (i <= totalFloors) {
                System.out.print("Costs for floor " + i + ": ");
                for (int j = 0; j < roomsPerFloor; j++) {
                    roomCost[i - 1][j] = input.nextInt();
                }
                for (int val : roomCost[i - 1]) {
                    if (val <= 0) {
                        System.out.println("Room costs must be positive.");
                        i--;
                        break;
                    }
                }
                i++;
            }
            System.out.println();
            boolean quit = false;
            String[][] guestInRoom = new String[totalFloors][roomsPerFloor];
            int[][] daysLeft = new int[totalFloors][roomsPerFloor];
            do {
                System.out.print("> ");
                String command = input.next();
                switch (command) {
                    case "in":
                        String guest = input.next();
                        int days = input.nextInt();
                        int floor = input.nextInt();
                        int room = input.nextInt();
                        boolean alreadyCheckedIn = false;
                        for (String[] row : guestInRoom) {
                            for (String col : row) {
                                if (col != null && col.equals(guest)) {
                                    alreadyCheckedIn = true;
                                    break;
                                }
                            }
                            if (alreadyCheckedIn) {
                                break;
                            }
                        }
                        if (alreadyCheckedIn) {
                            System.out.println(guest + " already checked in.");
                        } else if (floor > totalFloors || floor < 0 || room > roomsPerFloor || room < 0) {
                            System.out.println("Invalid floor or room.");
                        } else if (days < 1) {
                            System.out.println("Guests must stay at least one day");
                        } else if (guestInRoom[floor - 1][room - 1] != null) {
                            System.out.println("Room is already occupied.");
                        } else {
                            guestInRoom[floor - 1][room - 1] = guest;
                            daysLeft[floor - 1][room - 1] = days;
                            System.out.println("Checking in " + guest + " to floor " + floor + ", room " + room
                                    + ", for " + days + (days == 1 ? " day." : " days."));
                        }
                        break;

                    case "nd":
                        int guestCount = 0;
                        for (String[] row : guestInRoom) {
                            for (String col : row) {
                                if (col != null) {
                                    guestCount++;
                                }
                            }
                        }
                        int totalPayment = calculatePayment(guestInRoom, roomCost);
                        System.out.println("Total payment from " + guestCount + (guestCount == 1 ? " guest: "
                                : " guests: ") + "$" + totalPayment + ".00.");
                        for (floor = 0; floor < totalFloors; floor++) {
                            for (room = 0; room < roomsPerFloor; room++) {
                                if (daysLeft[floor][room] > 0) {
                                    daysLeft[floor][room]--;
                                    if (daysLeft[floor][room] == 0) {
                                        System.out.println("Checking out " + guestInRoom[floor][room] + " from floor "
                                                + (floor + 1) + ", " + "room " + (room + 1) + ".");
                                        guestInRoom[floor][room] = null;
                                    }
                                }

                            }
                        }
                        break;

                    case "price":
                        floor = input.nextInt();
                        room = input.nextInt();
                        if (floor > totalFloors || floor < 0 || room > roomsPerFloor || room < 0) {
                            System.out.println("Invalid floor or room.");
                        } else {
                            System.out.println("The price for floor " + floor + ", room " + room + " is $"
                                    + roomCost[floor - 1][room - 1] + ".00 per day.");
                        }
                        break;

                    case "print":
                        for (floor = guestInRoom.length - 1; floor >= 0; floor--) {
                            System.out.print("|");
                            for (room = 0; room < guestInRoom[floor].length; room++) {
                                if (guestInRoom[floor][room] == null) {
                                    System.out.print(" |");
                                } else {
                                    System.out.print(guestInRoom[floor][room] + "|");
                                }
                            }
                            System.out.println();
                        }
                        break;

                    case "quit":
                        quit = true;
                        break;

                    default:
                        break;
                }
            } while (!quit);
        }

    }

    /**
     * This is a method that receives two 2D arrays and returns the total payment that would be received from the
     * guests.
     *
     * @param guests stores the guests in the hotel
     * @param roomCost stores the cost of each room
     *
     * @return the total payment that would be received from the guests as an integer
     */
    public static int calculatePayment(String[][] guests, int[][] roomCost) {
        int totalPayment = 0;
        for (int a = 0; a < guests.length; a++) {
            for (int b = 0; b < guests[a].length; b++) {
                if (guests[a][b] != null) {
                    totalPayment += roomCost[a][b];
                }
            }
        }
        return totalPayment;
    }
}
