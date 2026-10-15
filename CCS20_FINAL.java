import java.util.Random;
import java.util.Scanner;

public class notfinal {

    // Shared Account Data
    static String storedName = "Juan";
    static String storedPassword = "pass123";
    static double balance = 10000.00;

    static Scanner input = new Scanner(System.in);
    static Random random = new Random();

    // Fixed array to track duplicate choices during a session
    static String[] selectedItems = new String[10];
    static int itemCount = 0;

    // Standard width for receipt box
    static final int RECEIPT_WIDTH = 69;

    // ===== 1. LOG-IN =====
    static boolean login() {
        int attempts = 0;

        printBorder();
        printRow("           E-WALLET LOG-IN");
        printBorder();

        while (attempts < 3) {
            System.out.print("\n  Name     : ");
            String name = input.nextLine().trim();
            System.out.print("  Password : ");
            String password = input.nextLine();

            if (name.equalsIgnoreCase(storedName) && password.equals(storedPassword)) {
                System.out.println("\n  Log-in successful! Welcome, " + storedName + ".");
                return true;
            }
            attempts++;
            System.out.println("\n  Incorrect name or password. Attempts left: " + (3 - attempts));
        }

        System.out.println("\n  Too many failed attempts. Goodbye!");
        return false;
    }

    // ===== 2. RANDOM PIN VERIFICATION =====
    static boolean verifyPin() {
        String pin = String.valueOf(1000 + random.nextInt(9000));
        int attempts = 0;

        System.out.println("\n  Your one-time PIN is: " + pin);

        while (attempts < 3) {
            System.out.print("  Enter PIN: ");
            String enteredPin = input.nextLine().trim();

            if (enteredPin.equals(pin)) {
                System.out.println("\n  PIN verified!");
                return true;
            }
            attempts++;
            System.out.println("  Wrong PIN. Attempts left: " + (3 - attempts));
        }

        System.out.println("\n  Too many wrong PIN attempts. Goodbye!");
        return false;
    }

    // ===== 3. HOME PAGE MENU =====
    static void homePage() {
        String choice;

        do {
            System.out.println();
            printBorder();
            printRow("               E-WALLET");
            printBorder();
            printRow("Welcome, " + storedName);
            printRow(String.format("Balance : PHP %,.2f", balance));
            printBorder();
            printRow("[1] Load");
            printRow("[0] Quit");
            printBorder();
            System.out.print("  Choose an option: ");
            choice = input.nextLine().trim();

            if (choice.equals("1")) {
                loadWallet();
            } else if (choice.equals("0")) {
                System.out.println("\n  Thank you for using E-Wallet. Goodbye!");
            } else {
                System.out.println("\n  Invalid option. Please choose 1 or 0.");
            }
        } while (!choice.equals("0"));
    }

    // ===== 4. LOAD / NUMBER DETECTION & SERVICE LOOP =====
    static void loadWallet() {
        while (true) {
            System.out.print("\nEnter your number (or '0' to go Back): ");
            String number = input.nextLine().trim();

            if (number.equals("0")) {
                return;
            }

            if (number.length() != 11 || !number.matches("\\d{11}")) {
                System.out.println("Invalid mobile number! Must be an 11-digit number (e.g., 09171234567).");
                continue;
            }

            String prefix = number.substring(0, 4);
            int provider = 0;

            if (prefix.equals("0817") || prefix.equals("0904") || prefix.equals("0905") ||
                prefix.equals("0906") || prefix.equals("0915") || prefix.equals("0916") ||
                prefix.equals("0917") || prefix.equals("0926") || prefix.equals("0927") ||
                prefix.equals("0935") || prefix.equals("0936") || prefix.equals("0937") ||
                prefix.equals("0945") || prefix.equals("0953") || prefix.equals("0954") ||
                prefix.equals("0955") || prefix.equals("0956") || prefix.equals("0965") ||
                prefix.equals("0966") || prefix.equals("0967") || prefix.equals("0975") ||
                prefix.equals("0976") || prefix.equals("0977") || prefix.equals("0978") ||
                prefix.equals("0979") || prefix.equals("0995") || prefix.equals("0996") ||
                prefix.equals("0997")) {
                
                System.out.println("Globe Provider Detected!");
                provider = 1;
            } 
            else if (prefix.equals("0907") || prefix.equals("0908") || prefix.equals("0909") ||
                     prefix.equals("0910") || prefix.equals("0912") || prefix.equals("0918") ||
                     prefix.equals("0919") || prefix.equals("0920") || prefix.equals("0921") ||
                     prefix.equals("0928") || prefix.equals("0929") || prefix.equals("0930") ||
                     prefix.equals("0938") || prefix.equals("0939") || prefix.equals("0946") ||
                     prefix.equals("0947") || prefix.equals("0948") || prefix.equals("0949") ||
                     prefix.equals("0950") || prefix.equals("0951") || prefix.equals("0961") ||
                     prefix.equals("0963") || prefix.equals("0968") || prefix.equals("0970") ||
                     prefix.equals("0981") || prefix.equals("0989") || prefix.equals("0998") ||
                     prefix.equals("0999")) {
                
                System.out.println("Smart Provider Detected!");
                provider = 2;
            } else {
                System.out.println("Invalid prefix! The number does not belong to Globe or Smart.");
                continue;
            }

            itemCount = 0;
            for (int i = 0; i < selectedItems.length; i++) {
                selectedItems[i] = null;
            }

            String orderSummary = "";
            double totalCost = 0.0;

            int another = 1;

            while (another == 1) {
                System.out.println();
                System.out.println("#####################");
                System.out.println("|  1. WiFi          |");
                System.out.println("|  2. Mobile Data   |");
                System.out.println("|  3. Cable         |");
                System.out.println("|  4. Landline      |");
                System.out.println("|  0. Back          |");
                System.out.println("#####################");

                System.out.print("Enter your service: ");
                int service;
                try {
                    service = Integer.parseInt(input.nextLine().trim());
                } catch (Exception e) {
                    System.out.println("Invalid input!");
                    continue;
                }

                // If user chooses 0 (Back) on Service Menu
                if (service == 0) {
                    if (itemCount > 0) {
                        while (true) {
                            System.out.println();
                            System.out.println("###############################");
                            System.out.println("| 1. Choose Another Service   |");
                            System.out.println("| 0. Finish Selection         |");
                            System.out.println("###############################");

                            System.out.print("Enter your choice: ");
                            String choiceStr = input.nextLine().trim();

                            if (choiceStr.equals("1")) {
                                another = 1;
                                break;
                            } else if (choiceStr.equals("0")) {
                                another = 0;
                                break;
                            } else {
                                System.out.println("Invalid choice. Please enter 1 or 0.");
                            }
                        }
                        continue;
                    } else {
                        break;
                    }
                }

                String itemName = "";
                double price = 0.0;
                String serviceBanner = "";

                // PROVIDER 1 (GLOBE)
                if (provider == 1) {
                    // WIFI
                    if (service == 1) {
                        System.out.println();
                        System.out.println("#####################################################################");
                        System.out.println("|  1. GFiber Plan 999 per Month       (25  Mbps)         PHP 999.00 |");
                        System.out.println("|  2. GFiber Unli Plan 1299 per Month (50  Mbps)        PHP 1299.00 |");
                        System.out.println("|  3. GFiber Unli Plan 1699 per Month (200 Mbps)        PHP 1699.00 |");
                        System.out.println("|  0. Back                                                          |");
                        System.out.println("#####################################################################");

                        System.out.print("Enter your plan: ");
                        int plan = Integer.parseInt(input.nextLine().trim());

                        if (plan == 1) {
                            itemName = "GFiber Plan 999 per Month (25 Mbps)";
                            price = 999.00;
                            serviceBanner = "Wifi Service Granted";
                        } else if (plan == 2) {
                            itemName = "GFiber Unli Plan 1299 per Month (50 Mbps)";
                            price = 1299.00;
                            serviceBanner = "Wifi Service Granted";
                        } else if (plan == 3) {
                            itemName = "GFiber Unli Plan 1699 per Month (200 Mbps)";
                            price = 1699.00;
                            serviceBanner = "Wifi Service Granted";
                        } else if (plan == 0) {
                            continue; // Back to Service Menu
                        } else {
                            System.out.println("Unavailable Plan");
                            continue;
                        }
                    }

                    // MOBILE DATA
                    else if (service == 2) {
                        System.out.println();
                        System.out.println("#####################################################################");
                        System.out.println("|  1. Go+99/7 Days   (20 Gb Data)                         PHP 99.00 |");
                        System.out.println("|  2. Go+179/15 Days (24 Gb Data)                        PHP 179.00 |");
                        System.out.println("|  3. Go+399/15 Days (48 Gb Data)                        PHP 399.00 |");
                        System.out.println("|  0. Back                                                          |");
                        System.out.println("#####################################################################");

                        System.out.print("Enter your plan: ");
                        int plan = Integer.parseInt(input.nextLine().trim());

                        if (plan == 1) {
                            itemName = "Go+99/7 Days (20 Gb Data)";
                            price = 99.00;
                            serviceBanner = "Mobile Data Service Granted";
                        } else if (plan == 2) {
                            itemName = "Go+179/15 Days (24 Gb Data)";
                            price = 179.00;
                            serviceBanner = "Mobile Data Service Granted";
                        } else if (plan == 3) {
                            itemName = "Go+399/15 Days (48 Gb Data)";
                            price = 399.00;
                            serviceBanner = "Mobile Data Service Granted";
                        } else if (plan == 0) {
                            continue; // Back to Service Menu
                        } else {
                            System.out.println("Unavailable Plan");
                            continue;
                        }
                    }

                    // CABLE
                    else if (service == 3) {
                        System.out.println();
                        System.out.println("#####################################################################");
                        System.out.println("|  1. Cartoon Network 99 per Month (Channel 37)           PHP 99.00 |");
                        System.out.println("|  2. Cinameone 149 per Month      (Channel 19)          PHP 149.00 |");
                        System.out.println("|  3. PBO 199 per Month            (Channel 8)           PHP 199.00 |");
                        System.out.println("|  0. Back                                                          |");
                        System.out.println("#####################################################################");

                        System.out.print("Enter your plan: ");
                        int plan = Integer.parseInt(input.nextLine().trim());

                        if (plan == 1) {
                            itemName = "Cartoon Network 99 per Month (Ch 37)";
                            price = 99.00;
                            serviceBanner = "Cable Service Granted";
                        } else if (plan == 2) {
                            itemName = "Cinameone 149 per Month (Ch 19)";
                            price = 149.00;
                            serviceBanner = "Cable Service Granted";
                        } else if (plan == 3) {
                            itemName = "PBO 199 per Month (Ch 8)";
                            price = 199.00;
                            serviceBanner = "Cable Service Granted";
                        } else if (plan == 0) {
                            continue; // Back to Service Menu
                        } else {
                            System.out.println("Unavailable Plan");
                            continue;
                        }
                    }

                    // LANDLINE
                    else if (service == 4) {
                        System.out.println();
                        System.out.println("#####################################################################");
                        System.out.println("|  1. Local Calls per Month                              PHP 299.00 |");
                        System.out.println("|  2. National Long-Distance Calls (NDD) per Month       PHP 399.00 |");
                        System.out.println("|  3. International Direct Dialing (IDD) per Month       PHP 499.00 |");
                        System.out.println("|  0. Back                                                          |");
                        System.out.println("#####################################################################");

                        System.out.print("Enter your plan: ");
                        int plan = Integer.parseInt(input.nextLine().trim());

                        if (plan == 1) {
                            itemName = "Local Calls per Month";
                            price = 299.00;
                            serviceBanner = "Landline Service Granted";
                        } else if (plan == 2) {
                            itemName = "National Long-Distance Calls (NDD)";
                            price = 399.00;
                            serviceBanner = "Landline Service Granted";
                        } else if (plan == 3) {
                            itemName = "International Direct Dialing (IDD)";
                            price = 499.00;
                            serviceBanner = "Landline Service Granted";
                        } else if (plan == 0) {
                            continue; // Back to Service Menu
                        } else {
                            System.out.println("Unavailable Plan");
                            continue;
                        }
                    } else {
                        System.out.println("Service Unavailable");
                        continue;
                    }
                }

                // PROVIDER 2 (SMART)
                else if (provider == 2) {
                    // WIFI
                    if (service == 1) {
                        System.out.println();
                        System.out.println("#####################################################################");
                        System.out.println("|  1. Home Plan 999 per Month  (600  Mbps)               PHP 999.00 |");
                        System.out.println("|  2. Home Plan 1499 per Month (1000  Mbps)             PHP 1499.00 |");
                        System.out.println("|  3. Home Plan 1999 per Month (1500 Mbps)              PHP 1999.00 |");
                        System.out.println("|  0. Back                                                          |");
                        System.out.println("#####################################################################");

                        System.out.print("Enter your plan: ");
                        int plan = Integer.parseInt(input.nextLine().trim());

                        if (plan == 1) {
                            itemName = "Home Plan 999 per Month (600 Mbps)";
                            price = 999.00;
                            serviceBanner = "Wifi Service Granted";
                        } else if (plan == 2) {
                            itemName = "Home Plan 1499 per Month (1000 Mbps)";
                            price = 1499.00;
                            serviceBanner = "Wifi Service Granted";
                        } else if (plan == 3) {
                            itemName = "Home Plan 1999 per Month (1500 Mbps)";
                            price = 1999.00;
                            serviceBanner = "Wifi Service Granted";
                        } else if (plan == 0) {
                            continue; // Back to Service Menu
                        } else {
                            System.out.println("Unavailable Plan");
                            continue;
                        }
                    }

                    // MOBILE DATA
                    else if (service == 2) {
                        System.out.println();
                        System.out.println("#####################################################################");
                        System.out.println("|  1. Power All YouTube+149/7 Days (Unli YT Data)        PHP 149.00 |");
                        System.out.println("|  2. Power All FB+109/7 Days      (Unli FB Data)        PHP 109.00 |");
                        System.out.println("|  3. Power All TikTok+109/7 Days  (Unli TikTok Data)    PHP 109.00 |");
                        System.out.println("|  0. Back                                                          |");
                        System.out.println("#####################################################################");

                        System.out.print("Enter your plan: ");
                        int plan = Integer.parseInt(input.nextLine().trim());

                        if (plan == 1) {
                            itemName = "Power All YouTube+149/7 Days (Unli YT)";
                            price = 149.00;
                            serviceBanner = "Mobile Data Service Granted";
                        } else if (plan == 2) {
                            itemName = "Power All FB+109/7 Days (Unli FB Data)";
                            price = 109.00;
                            serviceBanner = "Mobile Data Service Granted";
                        } else if (plan == 3) {
                            itemName = "Power All TikTok+109/7 Days (Unli TikTok)";
                            price = 109.00;
                            serviceBanner = "Mobile Data Service Granted";
                        } else if (plan == 0) {
                            continue; // Back to Service Menu
                        } else {
                            System.out.println("Unavailable Plan");
                            continue;
                        }
                    }

                    // CABLE
                    else if (service == 3) {
                        System.out.println();
                        System.out.println("#####################################################################");
                        System.out.println("|  1. HBO 129 per Month      (Channel 88)                PHP 129.00 |");
                        System.out.println("|  2. PBA Rush 149 per Month (Channel 50)                PHP 149.00 |");
                        System.out.println("|  3. GMA 199 per Month      (Channel 7)                 PHP 199.00 |");
                        System.out.println("|  0. Back                                                          |");
                        System.out.println("#####################################################################");

                        System.out.print("Enter your plan: ");
                        int plan = Integer.parseInt(input.nextLine().trim());

                        if (plan == 1) {
                            itemName = "HBO 129 per Month (Ch 88)";
                            price = 129.00;
                            serviceBanner = "Cable Service Granted";
                        } else if (plan == 2) {
                            itemName = "PBA Rush 149 per Month (Ch 50)";
                            price = 149.00;
                            serviceBanner = "Cable Service Granted";
                        } else if (plan == 3) {
                            itemName = "GMA 199 per Month (Ch 7)";
                            price = 199.00;
                            serviceBanner = "Cable Service Granted";
                        } else if (plan == 0) {
                            continue; // Back to Service Menu
                        } else {
                            System.out.println("Unavailable Plan");
                            continue;
                        }
                    }

                    // LANDLINE
                    else if (service == 4) {
                        System.out.println();
                        System.out.println("#####################################################################");
                        System.out.println("|  1. Local Calls per Month                              PHP 299.00 |");
                        System.out.println("|  2. National Long-Distance Calls (NDD) per Month       PHP 399.00 |");
                        System.out.println("|  3. International Direct Dialing (IDD) per Month       PHP 499.00 |");
                        System.out.println("|  0. Back                                                          |");
                        System.out.println("#####################################################################");

                        System.out.print("Enter your plan: ");
                        int plan = Integer.parseInt(input.nextLine().trim());

                        if (plan == 1) {
                            itemName = "Local Calls per Month";
                            price = 299.00;
                            serviceBanner = "Landline Service Granted";
                        } else if (plan == 2) {
                            itemName = "National Long-Distance Calls (NDD)";
                            price = 399.00;
                            serviceBanner = "Landline Service Granted";
                        } else if (plan == 3) {
                            itemName = "International Direct Dialing (IDD)";
                            price = 499.00;
                            serviceBanner = "Landline Service Granted";
                        } else if (plan == 0) {
                            continue; // Back to Service Menu
                        } else {
                            System.out.println("Unavailable Plan");
                            continue;
                        }
                    } else {
                        System.out.println("Service Unavailable");
                        continue;
                    }
                }

                // Process duplicate check & banner if item was picked
                if (!itemName.equals("")) {
                    boolean isDuplicate = false;
                    for (int i = 0; i < itemCount; i++) {
                        if (selectedItems[i] != null && selectedItems[i].equals(itemName)) {
                            isDuplicate = true;
                            break;
                        }
                    }

                    if (isDuplicate) {
                        System.out.println("\n[!] WARNING: You have already selected \"" + itemName + "\". Duplicate requests are not allowed.");
                    } else if (itemCount >= selectedItems.length) {
                        System.out.println("\n[!] WARNING: Maximum item limit of " + selectedItems.length + " reached for this transaction.");
                    } else {
                        selectedItems[itemCount++] = itemName;

                        printFixedBanner(serviceBanner);
                        System.out.println("Added: " + itemName);

                        String priceStr = String.format("PHP %,.2f", price);
                        int spacesNeeded = RECEIPT_WIDTH - 4 - itemName.length() - priceStr.length();
                        if (spacesNeeded < 1) spacesNeeded = 1;

                        orderSummary += "| " + itemName + " ".repeat(spacesNeeded) + priceStr + " |\n";
                        totalCost += price;
                    }
                }

                while (true) {
                    System.out.println();
                    System.out.println("###############################");
                    System.out.println("| 1. Choose Another Service   |");
                    System.out.println("| 0. Finish Selection         |");
                    System.out.println("###############################");

                    System.out.print("Enter your choice: ");
                    String choiceStr = input.nextLine().trim();

                    if (choiceStr.equals("1")) {
                        another = 1;
                        break;
                    } else if (choiceStr.equals("0")) {
                        another = 0;
                        break;
                    } else {
                        System.out.println("Invalid choice. Please enter 1 or 0.");
                    }
                }
            }

            System.out.println("Service selection completed.");

            if (!orderSummary.equals("")) {
                if (balance < totalCost) {
                    System.out.println("\n========================================================");
                    System.out.println("                  TRANSACTION ERROR                     ");
                    System.out.println("========================================================");
                    System.out.println("  Reason           : Insufficient Balance");
                    System.out.printf("  Total Amount Due : PHP %,.2f%n", totalCost);
                    System.out.printf("  Current Balance  : PHP %,.2f%n", balance);
                    System.out.println("  Status           : Transaction Cancelled");
                    System.out.println("========================================================\n");
                } else {
                    printReceipt(number, orderSummary, totalCost);

                    System.out.println("#################################################");
                    System.out.println("| 1. Start another transaction                  |");
                    System.out.println("| 0. Back to Home page                          |");
                    System.out.println("#################################################");
                    System.out.print("Enter your choice: ");
                    String postChoice = input.nextLine().trim();

                    if (postChoice.equals("0")) {
                        return;
                    }
                }
            } else {
                System.out.println("No services selected.");
            }
        }
    }

    // Helper method to draw aligned banner boxes
    static void printFixedBanner(String text) {
        System.out.println("\n##########################################");
        System.out.printf("|  %-36s  |%n", text);
        System.out.println("##########################################");
    }

    // Dynamic row format helpers for receipt
    static String formatReceiptRow(String label, String value) {
        String innerText = label + value;
        int spacesNeeded = RECEIPT_WIDTH - 4 - innerText.length();
        if (spacesNeeded < 0) spacesNeeded = 0;
        return "| " + innerText + " ".repeat(spacesNeeded) + " |\n";
    }

    static String formatReceiptCenter(String text) {
        int totalPadding = RECEIPT_WIDTH - 2 - text.length();
        int left = totalPadding / 2;
        int right = totalPadding - left;
        return "|" + " ".repeat(left) + text + " ".repeat(right) + "|\n";
    }

    // ===== 5. DESIGNED RECEIPT =====
    public static void printReceipt(String number, String orderDetails, double totalCost) {
        balance -= totalCost;

        String border = "+" + "-".repeat(RECEIPT_WIDTH - 2) + "+";

        System.out.println();
        System.out.println(border);
        System.out.print(formatReceiptCenter("E-WALLET RECEIPT"));
        System.out.println(border);
        System.out.print(formatReceiptRow("Customer Name : ", storedName));
        System.out.print(formatReceiptRow("Target Number : ", number));
        System.out.println(border);
        System.out.print(formatReceiptRow("Items Ordered:", ""));
        System.out.print(orderDetails);
        System.out.println(border);
        System.out.print(formatReceiptRow("Total Cost    : PHP ", String.format("%,.2f", totalCost)));
        System.out.print(formatReceiptRow("New Balance   : PHP ", String.format("%,.2f", balance)));
        System.out.println(border);
        System.out.print(formatReceiptCenter("Thank you for using E-Wallet!"));
        System.out.println(border);
        System.out.println();
    }

    // ===== DESIGN HELPERS =====
    static void printBorder() {
        System.out.println("+----------------------------------------+");
    }

    static void printRow(String text) {
        System.out.printf("| %-38s |%n", text);
    }

    // ===== MAIN =====
    public static void main(String[] args) {
        if (login()) {
            if (verifyPin()) {
                homePage();
            }
        }
        input.close();
    }
}