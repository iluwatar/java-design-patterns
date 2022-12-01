---
title: Model-View-Presenter
category: Architectural
language: en
tag:
 - Decoupling
---

## Intent
Apply a "Separation of Concerns" principle in a way that allows
developers to build and test user interfaces.

## Explanation

Real-world example

> Consider File selection application that allows to select a file from storage.
> File selection logic is completely separated from user interface implementation. 

In plain words

> It separates the UI completely from service/domain layer into Presenter. 

Wikipedia says

> Model–view–presenter (MVP) is a derivation of the model–view–controller (MVC) architectural pattern,
> and is used mostly for building user interfaces.

**Programmatic example**

Let's understand a simple file selection application build in AWT/Swing.
`FileLoader` reads & loads contain of given file. It represents the model component of MVP.

```java
public class FileLoader implements Serializable {

  /**
   * Generated serial version UID.
   */
  private static final long serialVersionUID = -4745803872902019069L;

  private static final Logger LOGGER = LoggerFactory.getLogger(FileLoader.class);

  /**
   * Indicates if the file is loaded or not.
   */
  private boolean loaded;

  /**
   * The name of the file that we want to load.
   */
  private String fileName;

  /**
   * Loads the data of the file specified.
   */
  public String loadData() {
    var dataFileName = this.fileName;
    try (var br = new BufferedReader(new FileReader(new File(dataFileName)))) {
      var result = br.lines().collect(Collectors.joining("\n"));
      this.loaded = true;
      return result;
    } catch (Exception e) {
      LOGGER.error("File {} does not exist", dataFileName);
    }

    return null;
  }

  /**
   * Sets the path of the file to be loaded, to the given value.
   *
   * @param fileName The path of the file to be loaded.
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  /**
   * Gets the path of the file to be loaded.
   *
   * @return fileName The path of the file to be loaded.
   */
  public String getFileName() {
    return this.fileName;
  }

  /**
   * Returns true if the given file exists.
   *
   * @return True, if the file given exists, false otherwise.
   */
  public boolean fileExists() {
    return new File(this.fileName).exists();
  }

  /**
   * Returns true if the given file is loaded.
   *
   * @return True, if the file is loaded, false otherwise.
   */
  public boolean isLoaded() {
    return this.loaded;
  }
}
```

`FileSelectorView` interface represents the View component in the MVP pattern. It can be
implemented by either the GUI components, or by the Stub. This is how it eases the UI testing.

```java
public interface FileSelectorView extends Serializable {

  /**
   * Opens the view.
   */
  void open();

  /**
   * Closes the view.
   */
  void close();

  /**
   * Returns true if view is opened.
   *
   * @return True, if the view is opened, false otherwise.
   */
  boolean isOpened();

  /**
   * Sets the presenter component, to the one given as parameter.
   *
   * @param presenter The new presenter component.
   */
  void setPresenter(FileSelectorPresenter presenter);

  /**
   * Gets presenter component.
   *
   * @return The presenter Component.
   */
  FileSelectorPresenter getPresenter();

  /**
   * Sets the file's name, to the value given as parameter.
   *
   * @param name The new name of the file.
   */
  void setFileName(String name);

  /**
   * Gets the name of file.
   *
   * @return The name of the file.
   */
  String getFileName();

  /**
   * Displays a message to the users.
   *
   * @param message The message to be displayed.
   */
  void showMessage(String message);

  /**
   * Displays the data to the view.
   *
   * @param data The data to be written.
   */
  void displayData(String data);
}
```

`FileSelectorJFrame` represents the GUI implementation of the View component in the MVP pattern.

```java
public class FileSelectorJFrame extends JFrame implements FileSelectorView, ActionListener {

  /**
   * Default serial version ID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The "OK" button for loading the file.
   */
  private final JButton ok;

  /**
   * The cancel button.
   */
  private final JButton cancel;

  /**
   * The text field for giving the name of the file that we want to open.
   */
  private final JTextField input;

  /**
   * A text area that will keep the contents of the file opened.
   */
  private final JTextArea area;

  /**
   * The Presenter component that the frame will interact with.
   */
  private FileSelectorPresenter presenter;

  /**
   * The name of the file that we want to read it's contents.
   */
  private String fileName;

  /**
   * Constructor.
   */
  public FileSelectorJFrame() {
    super("File Loader");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(null);
    this.setBounds(100, 100, 500, 200);

    /*
     * Add the panel.
     */
    var panel = new JPanel();
    panel.setLayout(null);
    this.add(panel);
    panel.setBounds(0, 0, 500, 200);
    panel.setBackground(Color.LIGHT_GRAY);

    /*
     * Add the info label.
     */
    var info = new JLabel("File Name :");
    panel.add(info);
    info.setBounds(30, 10, 100, 30);

    /*
     * Add the contents label.
     */
    var contents = new JLabel("File contents :");
    panel.add(contents);
    contents.setBounds(30, 100, 120, 30);

    /*
     * Add the text field.
     */
    this.input = new JTextField(100);
    panel.add(input);
    this.input.setBounds(150, 15, 200, 20);

    /*
     * Add the text area.
     */
    this.area = new JTextArea(100, 100);
    var pane = new JScrollPane(area);
    pane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
    pane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
    panel.add(pane);
    this.area.setEditable(false);
    pane.setBounds(150, 100, 250, 80);

    /*
     * Add the OK button.
     */
    this.ok = new JButton("OK");
    panel.add(ok);
    this.ok.setBounds(250, 50, 100, 25);
    this.ok.addActionListener(this);

    /*
     * Add the cancel button.
     */
    this.cancel = new JButton("Cancel");
    panel.add(this.cancel);
    this.cancel.setBounds(380, 50, 100, 25);
    this.cancel.addActionListener(this);

    this.presenter = null;
    this.fileName = null;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (this.ok.equals(e.getSource())) {
      this.fileName = this.input.getText();
      presenter.fileNameChanged();
      presenter.confirmed();
    } else if (this.cancel.equals(e.getSource())) {
      presenter.cancelled();
    }
  }

  @Override
  public void open() {
    this.setVisible(true);
  }

  @Override
  public void close() {
    this.dispose();
  }

  @Override
  public boolean isOpened() {
    return this.isVisible();
  }

  @Override
  public void setPresenter(FileSelectorPresenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public FileSelectorPresenter getPresenter() {
    return this.presenter;
  }

  @Override
  public void setFileName(String name) {
    this.fileName = name;
  }

  @Override
  public String getFileName() {
    return this.fileName;
  }

  @Override
  public void showMessage(String message) {
    JOptionPane.showMessageDialog(null, message);
  }

  @Override
  public void displayData(String data) {
    this.area.setText(data);
  }
}
```

`FileSelectorStub` is a stub that implements the View interface and it is useful when we want to test the reaction to
user events, such as mouse clicks etc.

```java
public class FileSelectorStub implements FileSelectorView {

    /**
     * Indicates whether or not the view is opened.
     */
    private boolean opened;

    /**
     * The presenter Component.
     */
    private FileSelectorPresenter presenter;

    /**
     * The current name of the file.
     */
    private String name;

    /**
     * Indicates the number of messages that were "displayed" to the user.
     */
    private int numOfMessageSent;

    /**
     * Indicates if the data of the file where displayed or not.
     */
    private boolean dataDisplayed;

    /**
     * Constructor.
     */
    public FileSelectorStub() {
        this.opened = false;
        this.presenter = null;
        this.name = "";
        this.numOfMessageSent = 0;
        this.dataDisplayed = false;
    }

    @Override
    public void open() {
        this.opened = true;
    }

    @Override
    public void setPresenter(FileSelectorPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean isOpened() {
        return this.opened;
    }

    @Override
    public FileSelectorPresenter getPresenter() {
        return this.presenter;
    }

    @Override
    public String getFileName() {
        return this.name;
    }

    @Override
    public void setFileName(String name) {
        this.name = name;
    }

    @Override
    public void showMessage(String message) {
        this.numOfMessageSent++;
    }

    @Override
    public void close() {
        this.opened = false;
    }

    @Override
    public void displayData(String data) {
        this.dataDisplayed = true;
    }

    /**
     * Returns the number of messages that were displayed to the user.
     */
    public int getMessagesSent() {
        return this.numOfMessageSent;
    }

    /**
     * Returns true, if the data were displayed.
     *
     * @return True if the data where displayed, false otherwise.
     */
    public boolean dataDisplayed() {
        return this.dataDisplayed;
    }
}
```

`FileSelectorPresenter` represents the Presenter component in the MVP pattern. 
It is responsible for reacting to the user's actions and update the View component.

```java
public class FileSelectorPresenter implements Serializable {

  /**
   * Generated serial version UID.
   */
  private static final long serialVersionUID = 1210314339075855074L;

  /**
   * The View component that the presenter interacts with.
   */
  private final FileSelectorView view;

  /**
   * The Model component that the presenter interacts with.
   */
  private FileLoader loader;

  /**
   * Constructor.
   *
   * @param view The view component that the presenter will interact with.
   */
  public FileSelectorPresenter(FileSelectorView view) {
    this.view = view;
  }

  /**
   * Sets the {@link FileLoader} object, to the value given as parameter.
   *
   * @param loader The new {@link FileLoader} object(the Model component).
   */
  public void setLoader(FileLoader loader) {
    this.loader = loader;
  }

  /**
   * Starts the presenter.
   */
  public void start() {
    view.setPresenter(this);
    view.open();
  }

  /**
   * An "event" that fires when the name of the file to be loaded changes.
   */
  public void fileNameChanged() {
    loader.setFileName(view.getFileName());
  }

  /**
   * Ok button handler.
   */
  public void confirmed() {
    if (loader.getFileName() == null || loader.getFileName().equals("")) {
      view.showMessage("Please give the name of the file first!");
      return;
    }

    if (loader.fileExists()) {
      var data = loader.loadData();
      view.displayData(data);
    } else {
      view.showMessage("The file specified does not exist.");
    }
  }

  /**
   * Cancels the file loading process.
   */
  public void cancelled() {
    view.close();
  }
}
```

Below code reflects how we wire-up the Presenter & the View and the Presenter & the Model.

```java
    var loader = new FileLoader();
    var frame = new FileSelectorJFrame();
    var presenter = new FileSelectorPresenter(frame);
    presenter.setLoader(loader);
    presenter.start();
```

## Class diagram
![alt text](./etc/model-view-presenter_1.png "Model-View-Presenter")

## Applicability
Use the Model-View-Presenter in any of the following
situations

* When you want to improve the "Separation of Concerns" principle in presentation logic
* When a user interface development and testing is necessary.

