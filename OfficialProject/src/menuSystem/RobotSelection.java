package menuSystem;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import ExternalTools.JsonParser;
import gameInitializer.MultiPlayerGameInitInfo;

import java.awt.Font;

/**
 * The Code to set up the robot selection menu This menu also handles interaction with the robot
 * librarian This class is incomplete
 * 
 * @author Bryton
 *
 */
public class RobotSelection extends Menu {

  /**
   * List of strings that holds all of the JSON represented data formats of each robot Segmented
   * into three lists, one list for each class of robot
   */
  private ArrayList<String> scoutRobotList;
  private ArrayList<String> tankRobotList;
  private ArrayList<String> sniperRobotList;
  private ArrayList<String> allRobotList;

  /**
   * list of all of the robots that can be added The string is the code, which is the name of the
   * robot The HashMap represents the HashMap returned by gson
   */
  private HashMap<String, HashMap<String, Object>> infoMap;

  /**
   * The final list of robots, referencing the ones the user selected when confirm was clicked.
   */
  private ArrayList<String> finalList;

  /**
   * A scrollable list that represents either scoutRobotList, tankRobotList, or sniperRobotList It's
   * displayed on the panel and allows the system user to click on it and select which robots they
   * would like to use
   */
  private JList<String> scrollableList;

  /** The name of the user chosen CPU1Tank */
  private String CPU1Tank;

  /** The name of the user chosen CPU1sniper */
  private String CPU1Sniper;

  /** The name of the user chosen CPU1Scout */
  private String CPU1Scout;

  /** The name of the user chosen CPU2Tank */
  private String CPU2Tank;

  /** The name of the user chosen CPU2Sniper */
  private String CPU2Sniper;

  /** The name of the user chosen CPU2Tank */
  private String CPU2Scout;

  /** True if the user is setting a tank robot, false otherwise */
  private boolean tankFlag;

  /** True if the user is setting a sniper robot, false otherwise */
  private boolean sniperFlag;

  /** True if the user is setting a scout robot, false otherwise */
  private boolean scoutFlag;

  /** A panel that contains the components that allow the user to select robots */
  protected JPanel selectPanel;

  /** A panel that contains the information of the robot selected in the JList */
  protected JPanel selectedRobotDisplay;

  /**
   * Constructor that initializes the menu and adds all of the base robots to the lists
   */
  public RobotSelection() {
    super();

    infoMap = new HashMap<String, HashMap<String, Object>>();
    scoutRobotList = new ArrayList<String>();
    tankRobotList = new ArrayList<String>();
    sniperRobotList = new ArrayList<String>();
    allRobotList = new ArrayList<String>();
    finalList = new ArrayList<String>();

    initializeMenu();
  }

  /**
   * a method used to help the constructor initialize the menu.
   */
  private void initializeMenu() {
    myPanel = new JPanel();
    myPanel.setLayout(null);

    // The panel that displays the robot selection menu
    selectPanel = new JPanel();
    selectPanel.setBounds(6, 10, 316, 150);
    myPanel.add(selectPanel);
    selectPanel.setLayout(null);

    // The headings for each of the columns/rows
    JLabel scoutHeading = new JLabel("Scouts");
    scoutHeading.setBounds(64, 6, 44, 16);
    selectPanel.add(scoutHeading);

    JLabel sniperHeading = new JLabel("Snipers");
    sniperHeading.setBounds(153, 6, 46, 16);
    selectPanel.add(sniperHeading);

    JLabel tankHeading = new JLabel("Tanks");
    tankHeading.setBounds(250, 6, 40, 16);
    selectPanel.add(tankHeading);

    JLabel cpu1Heading = new JLabel("CPU 1");
    cpu1Heading.setBounds(6, 44, 44, 16);
    selectPanel.add(cpu1Heading);

    // The buttons that allow the user to set the CPU1 unit scripts
    JButton setCPU1Scout = new JButton("Set Scout");
    setCPU1Scout.setBounds(45, 33, 80, 40);
    setCPU1Scout.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (!scoutFlag) {
          scoutSort();
          sniperFlag = false;
          tankFlag = false;
          scoutFlag = true;
        } else {
          CPU1Scout = scrollableList.getSelectedValue();
          setCPU1Scout.setText(scrollableList.getSelectedValue());
        }
      }
    });
    selectPanel.add(setCPU1Scout);

    JButton setCPU1Sniper = new JButton("Set Sniper");
    setCPU1Sniper.setBounds(137, 33, 80, 40);
    setCPU1Sniper.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (!sniperFlag) {
          sniperSort();
          sniperFlag = true;
          tankFlag = false;
          scoutFlag = false;
        } else {
          CPU1Sniper = scrollableList.getSelectedValue();
          setCPU1Sniper.setText(scrollableList.getSelectedValue());
        }
      }
    });
    selectPanel.add(setCPU1Sniper);

    JButton setCPU1Tank = new JButton("Set Tank");
    setCPU1Tank.setBounds(230, 33, 80, 40);
    setCPU1Tank.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (!tankFlag) {
          tankSort();
          sniperFlag = false;
          tankFlag = true;
          scoutFlag = false;
        } else {
          CPU1Tank = scrollableList.getSelectedValue();
          setCPU1Tank.setText(scrollableList.getSelectedValue());
        }
      }
    });
    selectPanel.add(setCPU1Tank);

    // The buttons that allow the user to set the CPU2 unit scripts
    JLabel cpu2Heading = new JLabel("CPU 2");
    cpu2Heading.setBounds(6, 103, 44, 16);
    selectPanel.add(cpu2Heading);

    JButton setCPU2Scout = new JButton("Set Scout");
    setCPU2Scout.setBounds(45, 92, 80, 40);
    setCPU2Scout.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (!scoutFlag) {
          scoutSort();
          sniperFlag = false;
          tankFlag = false;
          scoutFlag = true;
        } else {
          CPU2Scout = scrollableList.getSelectedValue();
          setCPU2Scout.setText(scrollableList.getSelectedValue());
        }
      }
    });
    selectPanel.add(setCPU2Scout);

    JButton setCPU2Sniper = new JButton("Set Sniper");
    setCPU2Sniper.setBounds(137, 92, 80, 40);
    setCPU2Sniper.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (!sniperFlag) {
          sniperSort();
          sniperFlag = true;
          tankFlag = false;
          scoutFlag = false;
        } else {
          CPU2Sniper = scrollableList.getSelectedValue();
          setCPU2Sniper.setText(scrollableList.getSelectedValue());
        }
      }
    });
    selectPanel.add(setCPU2Sniper);

    JButton setCPU2Tank = new JButton("Set Tank");
    setCPU2Tank.setBounds(230, 92, 80, 40);
    setCPU2Tank.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (!tankFlag) {
          tankSort();
          sniperFlag = false;
          tankFlag = true;
          scoutFlag = false;
        } else {
          CPU2Tank = scrollableList.getSelectedValue();
          setCPU2Tank.setText(scrollableList.getSelectedValue());
        }
      }
    });
    selectPanel.add(setCPU2Tank);

    // Adding a set defaults button to make testing easier
    JButton setDefaults = new JButton("Default Selection");
    setDefaults.setBounds(120, 160, 120, 40);
    setDefaults.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        CPU1Scout = "Creeper";
        setCPU1Scout.setText("Creeper");
        CPU2Scout = "Creeper";
        setCPU2Scout.setText("Creeper");

        CPU1Sniper = "NeutralCreeper";
        setCPU1Sniper.setText("NeutralCreeper");
        CPU2Sniper = "NeutralCreeper";
        setCPU2Sniper.setText("NeutralCreeper");

        CPU1Tank = "Centralizer";
        setCPU1Tank.setText("Centralizer");
        CPU2Tank = "Centralizer";
        setCPU2Tank.setText("Centralizer");
      }
    });
    myPanel.add(setDefaults);

    // The panel that will display the selected robot information in more detail
    selectedRobotDisplay = new JPanel();
    selectedRobotDisplay.setLayout(null);
    selectedRobotDisplay.setBounds(64, 218, 490, 98);
    selectedRobotDisplay.setOpaque(false);
    myPanel.add(selectedRobotDisplay);

    // Get the intial list, parse it, and add it to the panel properly
    getInitialRobotList();
    // Now robotList should be initialized, so we can create a scroll pane that has each individual
    // robot in it.

    allRobotList.addAll(infoMap.keySet());

    scrollableList = new JList<String>();
    initializeScrollableList(allRobotList);
    scrollableList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    scrollableList.setLayoutOrientation(JList.VERTICAL);
    scrollableList.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent mouseEvent) {
        String robotToDisplay = scrollableList.getSelectedValue();
        informationDisplay(robotToDisplay);
      }
    });
    JScrollPane scroller = new JScrollPane(scrollableList);
    scroller.setBounds(334, 6, 302, 180);
    myPanel.add(scroller);

    // Setting up the goBack button
    JButton goBackButton = new JButton("Go Back");
    goBackButton.setBounds(0, 330, 80, 30);
    // Adding an action listener to the goback button
    // When the button is pressed, call the goBackClick() method.
    goBackButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        goBackClick();
      }
    });
    myPanel.add(goBackButton);

    // Setting up the Confirm Button
    JButton launchGameButton = new JButton("Launch Game");
    launchGameButton.setBounds(515, 330, 120, 30);
    // Adding an action listener to the rules button
    // When the button is pressed, call the rulesClick() method
    launchGameButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        launchGameClick();
      }
    });
    myPanel.add(launchGameButton);

    // Setting up the rules Button
    JButton RulesButton = new JButton("Rules");
    RulesButton.setBounds(280, 330, 80, 30);
    // Adding an action listener to the rules button
    // When the button is pressed, call the rulesClick() method
    RulesButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        rulesClick();
      }
    });
    myPanel.add(RulesButton);

    // Setting up the background image,
    JLabel backgroundLabel = new JLabel("");
    Image scaledImage = menuImage.getScaledInstance(MenuController.WIDTH, MenuController.HEIGHT,
        Image.SCALE_DEFAULT);
    ImageIcon icon = new ImageIcon(scaledImage);
    backgroundLabel.setIcon(icon);
    backgroundLabel.setBounds(0, 0, MenuController.WIDTH, MenuController.HEIGHT);
    myPanel.add(backgroundLabel);
    myPanel.repaint();
  }

  /**
   * Helper method, sets the scrollable List to be everything in the specified arrayList
   * @param listToDisplay List to display
   */
  private void initializeScrollableList(ArrayList<String> listToDisplay) {
    String[] testing = new String[listToDisplay.size()];
    for (int i = 0; i < listToDisplay.size(); i++) {
      testing[i] = listToDisplay.get(i);
    }
    scrollableList.setListData(testing);
  }

  /**
   * update the panel to display the information about the selected robot
   * 
   * @param robotToDisplay the robot we are displaying.
   */
  private void informationDisplay(String robotToDisplay) {
    HashMap<String, Object> robotMap = infoMap.get(robotToDisplay);
    selectedRobotDisplay.removeAll();

    JLabel nameLabel = new JLabel("Information for: " + robotToDisplay);
    nameLabel.setBounds(10, 10, 470, 30);
    nameLabel.setFont(new Font("Sarif", Font.BOLD, 20));
    selectedRobotDisplay.add(nameLabel);

    JLabel classLabel = new JLabel("Class: " + robotMap.get("class"));
    classLabel.setBounds(10, 40, 200, 20);
    selectedRobotDisplay.add(classLabel);

    JLabel groupLabel = new JLabel("Team: " + robotMap.get("team"));
    groupLabel.setBounds(10, 70, 200, 20);
    selectedRobotDisplay.add(groupLabel);

    JLabel matchesLabel = new JLabel("Matches Played: " + robotMap.get("matches"));
    matchesLabel.setBounds(210, 40, 200, 20);
    selectedRobotDisplay.add(matchesLabel);

    JLabel winsLabel = new JLabel("Wins: " + robotMap.get("wins"));
    winsLabel.setBounds(210, 70, 200, 20);
    selectedRobotDisplay.add(winsLabel);

    JLabel lossesLabel = new JLabel("Losses: " + robotMap.get("losses"));
    lossesLabel.setBounds(410, 40, 200, 20);
    selectedRobotDisplay.add(lossesLabel);

    JLabel killsLabel = new JLabel("kills: " + robotMap.get("killed"));
    killsLabel.setBounds(410, 70, 200, 20);
    selectedRobotDisplay.add(killsLabel);

    selectedRobotDisplay.repaint();
  }

  /**
   * Method used to communicate with the robot librarian and get the list of robots
   */
  private void getInitialRobotList() {
    Socket robotLibrarian = null;
    DataOutputStream outputStream = null;
    BufferedReader inputStream = null;
    String jsonString = "";

    // Step one, set up the socket and our input/output streams
    try {
      robotLibrarian = new Socket("gpu0.usask.ca", 20001);
      outputStream = new DataOutputStream(robotLibrarian.getOutputStream());
      inputStream = new BufferedReader(new InputStreamReader(robotLibrarian.getInputStream()));
    } catch (UnknownHostException e) {
      JOptionPane.showMessageDialog(new JFrame(),
          "The Host is probably unknown or some other error: " + e.getMessage());
    } catch (IOException e) {
      JOptionPane.showMessageDialog(new JFrame(),
          "Could not establish a connection to the Robot Librarian."
              + "\nMake sure that the tunnel to the USASK network is turned on!"
              + "\nHere is the error Message: " + e.getMessage());
    }

    // If everything set up correctly, we can send our JSON request!
    if (null != robotLibrarian && null != outputStream && null != inputStream) {
      try {
        // Send the request for the full list of data
        outputStream.writeBytes("{ \"list-request\" : { \"data\" : \"full\" } }" + '\n');

        // then wait for it.
        String testString = null;
        while (true) {
          testString = inputStream.readLine();
          if (null == testString) {
            break;
          }
          jsonString += testString;
        }

        // Step four, clean up the sockets
        inputStream.close();
        outputStream.close();
        robotLibrarian.close();
      } catch (IOException e) {
        JOptionPane.showMessageDialog(new JFrame(),
            "Something failed during communication: " + e.getMessage());
      }
    }
    // Parse the JSON string now
    jsonString = jsonString.substring(1, jsonString.length() - 1);
    getJson(jsonString);
  }

  /**
   * Sort the scrollable list so that it displays only the sniper robots
   */
  private void sniperSort() {
    if (0 == sniperRobotList.size()) {
      for (String currentRobot : allRobotList) {
        if (infoMap.get(currentRobot).get("class").equals("Sniper")) {
          sniperRobotList.add(currentRobot);
        }
      }
    }
    initializeScrollableList(sniperRobotList);
  }

  /**
   * Sort the scrollable list so that it displays only the tank robots
   */
  private void tankSort() {
    if (0 == tankRobotList.size()) {
      for (String currentRobot : allRobotList) {
        if (infoMap.get(currentRobot).get("class").equals("Tank")) {
          tankRobotList.add(currentRobot);
        }
      }
    }
    initializeScrollableList(tankRobotList);
  }

  /**
   * Sort the scrollable list so that it displays only the scout robots
   * 
   */
  private void scoutSort() {
    if (0 == scoutRobotList.size()) {
      for (String currentRobot : allRobotList) {
        if (infoMap.get(currentRobot).get("class").equals("Scout")) {
          scoutRobotList.add(currentRobot);
        }
      }
    }
    initializeScrollableList(scoutRobotList);
  }

  /**
   * method that when given a jsonString of ALL the json objects, it will parse the string and using
   * JsonParser to create a bunch of hashmaps, then it will add each one of those hashmaps to our
   * collection of hashmaps.
   * 
   * @param jsonString the string at which we are creating the robot hashmpas from
   */
  private void getJson(String jsonString) {
    Stack<Integer> locationStack = new Stack<Integer>();

    // first, we want to find all of the script starts within the robot librarian response.
    // This is all strings that start with {"script" and then find every substring that matches this
    // pushing that value onto a stack
    String toMatch = jsonString.substring(0, 9);
    for (int i = 0; i < jsonString.length() - 9; i++) {
      if (jsonString.substring(i, i + 9).equals(toMatch)) {
        locationStack.push(i);
      }
    }
    // Then we also add the length of the string-1 onto the stack
    locationStack.push(jsonString.length() - 1);

    // Then for each pair of start/end positions, we get that substring and push it onto a new stack
    // Each one of the elements on the new stack correspond to the entire robot script
    int startPos;
    int endPos;
    Stack<String> stringStack = new Stack<String>();
    String robotString = null;
    while (locationStack.size() >= 2) {
      endPos = locationStack.pop();
      startPos = locationStack.peek();

      robotString = jsonString.substring(startPos, endPos);
      stringStack.push(robotString);
    }

    // Now, for each script on the stack, if it isn't the last item we have to remove the last two
    // characters, which are },
    // We don't want these, but the last item on the stack won't have this
    // We then use our JsonParser to create a hashmap corresponding of each of these scripts
    // And finally, we add this hashmap and the name of the current robot to our infomap, which
    // the rest of the program uses.
    String currentBot = null;
    while (false == stringStack.empty()) {
      if (stringStack.size() != 1) {
        currentBot = stringStack.pop();
        currentBot = currentBot.substring(10, currentBot.length() - 2);
        currentBot += "\n";
      } else {
        currentBot = stringStack.pop();
        currentBot = currentBot.substring(10, currentBot.length());
        currentBot += "\n";
      }
      HashMap<String, Object> currentBotMap = JsonParser.jsonToHashMap(currentBot);
      infoMap.put((String) currentBotMap.get("name"), currentBotMap);
    }
  }

  /**
   * Method that is called when the launch game button is pressed. Note: The lists will be in the
   * order of tank/scout/sniper
   */
  @SuppressWarnings("unused") // we can add this because the constructor for the game inits start
  // the game.
  private void launchGameClick() {
    MultiPlayerGameInitInfo myGameInitInfo = null;
    switch (MenuController.numAI) {
      case (0):
        myGameInitInfo = new MultiPlayerGameInitInfo(0, finalList, MenuController.boardSize,
            MenuController.numPlayers);
        break;
      case (1):
        if (null == CPU1Scout) {
          JOptionPane.showMessageDialog(new JFrame(), "Verify that the CPU1 scout is set!");
        } else if (null == CPU1Sniper) {
          JOptionPane.showMessageDialog(new JFrame(), "Verify that the CPU1 sniper is set!");
        } else if (null == CPU1Tank) {
          JOptionPane.showMessageDialog(new JFrame(), "Verify that the CPU1 tank is set!");
        } else {
          addCPUToList(CPU1Tank);
          addCPUToList(CPU1Scout);
          addCPUToList(CPU1Sniper);

          myGameInitInfo = new MultiPlayerGameInitInfo(MenuController.numAI, finalList,
              MenuController.boardSize, MenuController.numPlayers);
        }
        break;
      case (2):
        if (null == CPU1Scout) {
          JOptionPane.showMessageDialog(new JFrame(), "Verify that the CPU1 scout is set!");
        } else if (null == CPU1Sniper) {
          JOptionPane.showMessageDialog(new JFrame(), "Verify that the CPU1 sniper is set!");
        } else if (null == CPU1Tank) {
          JOptionPane.showMessageDialog(new JFrame(), "Verify that the CPU1 tank is set!");
        } else if (null == CPU2Scout) {
          JOptionPane.showMessageDialog(new JFrame(), "Verify that the CPU2 scout is set!");
        } else if (null == CPU2Sniper) {
          JOptionPane.showMessageDialog(new JFrame(), "Verify that the CPU2 sniper is set!");
        } else if (null == CPU2Tank) {
          JOptionPane.showMessageDialog(new JFrame(), "Verify that the CPU2 tank is set!");
        } else {
          addCPUToList(CPU1Tank);
          addCPUToList(CPU1Scout);
          addCPUToList(CPU1Sniper);
          addCPUToList(CPU2Tank);
          addCPUToList(CPU2Scout);
          addCPUToList(CPU2Sniper);

          myGameInitInfo = new MultiPlayerGameInitInfo(MenuController.numAI, finalList,
              MenuController.boardSize, MenuController.numPlayers);
        }
        break;
      default:
        break;
    }
  }

  /**
   * Given the name of a robot to add, we look up its code from the hashmap.
   * 
   * @param cpuToAdd CPU to add
   */
  private void addCPUToList(String cpuToAdd) {

    @SuppressWarnings("unchecked") // We have this because gson returns a hashmap of objects, but
    // we know what they are and how they behave
    ArrayList<String> robotString = (ArrayList<String>) infoMap.get(cpuToAdd).get("code");
    String stringToAdd = "";
    for (String e : robotString) {
      stringToAdd += e;
      stringToAdd += " ";
    }
    finalList.add(stringToAdd);
  }

  /**
   * A method for when the rules button is clicked
   */
  private void rulesClick() {
    MenuController.addToStack(MenuFactory.getRules());
  }

  /**
   * Method is used for testing Note: this method doesn't test full method functionality it is only
   * used to test that the menu draws properly To test full menu system functionality, run the
   * MenuController tests and click through the menu system.
   * 
   * @param args Unused
   */
  public static void main(String args[]) {
    JFrame testFrame = new JFrame();

    MenuController.setnumAI(1);
    testFrame.setTitle("Testing Drawing the robot selection menu");
    testFrame.setResizable(false);
    testFrame.setSize(MenuController.WIDTH, MenuController.HEIGHT);
    RobotSelection testPanel = new RobotSelection();
    testFrame.getContentPane().add(testPanel.getMenuPanel());
    testFrame.setLocation((int) (0.25 * Toolkit.getDefaultToolkit().getScreenSize().getWidth()),
        (int) (0.25 * Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
    testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    testFrame.setVisible(true);
  }
}
