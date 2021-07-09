---
layout: pattern
title: Presentation
folder: presentation
permalink: /patterns/presentation/zh
categories: Behavioral
language: zh
tags:
 - Decoupling
---
## 又称为
应用模型

## 目的
表示模型将视图的状态和行为拉出到作为表示一部分的模型类中。

## 解释

真实世界的例子

> 当我们需要用GUI写程序的时候，我们不需要把所有的表现行为都放在视图类中。因为它会测试变得更难。所以我们可以使用Presentation Model Pattern来分离行为和视图。视图只需要从其他类加载数据和状态，并根据状态在屏幕上显示这些数据。

简单来说

> 一种用于划分演示和控制的模式。

代码示例

`view` 类是相册的 GUI。方法 `saveToPMod` 和 `loadFromPMod` 用于实现同步。

```java
public class View {
  /**
   * the model that controls this view.
   */
  private final PresentationModel model;

  private TextField txtTitle;
  private TextField txtArtist;
  private JCheckBox chkClassical;
  private TextField txtComposer;
  private JList<String> albumList;
  private JButton apply;
  private JButton cancel;

  public View() {
    model = new PresentationModel(PresentationModel.albumDataSet());
  }

  /**
   * save the data to PresentationModel.
   */
  public void saveToPMod() {
    LOGGER.info("Save data to PresentationModel");
    model.setArtist(txtArtist.getText());
    model.setTitle(txtTitle.getText());
    model.setIsClassical(chkClassical.isSelected());
    model.setComposer(txtComposer.getText());
  }

  /**
   * load the data from PresentationModel.
   */
  public void loadFromPMod() {
    LOGGER.info("Load data from PresentationModel");
    txtArtist.setText(model.getArtist());
    txtTitle.setText(model.getTitle());
    chkClassical.setSelected(model.getIsClassical());
    txtComposer.setEditable(model.getIsClassical());
    txtComposer.setText(model.getComposer());
  }

  public void createView() {
    // the detail of GUI information like size, listenser and so on.
  }
}
```

Class `Album` is to store information of a album.

```java
public class Album {
    
  private String title;
  private String artist;
  private boolean isClassical;
  /**
   * only when the album is classical,
   * composer can have content.
   */
  private String composer;
}

```

DisplateAlbums 类存储将在 GUI 上显示的所有专辑的信息。

```java
public class DisplayedAlbums {
  private final List<Album> albums;

  public DisplayedAlbums() {
    this.albums = new ArrayList<>();
  }

  public void addAlbums(final String title,
                        final String artist, final boolean isClassical,
                        final String composer) {
    if (isClassical) {
      this.albums.add(new Album(title, artist, true, composer));
    } else {
      this.albums.add(new Album(title, artist, false, ""));
    }
  }
}
```
`PresentationMod` 类用于控制 GUI 的所有动作。

```java
public class PresentationModel {
  private final DisplayedAlbums data;
  
  private int selectedAlbumNumber;
  private Album selectedAlbum;

  public PresentationModel(final DisplayedAlbums dataOfAlbums) {
    this.data = dataOfAlbums;
    this.selectedAlbumNumber = 1;
    this.selectedAlbum = this.data.getAlbums().get(0);
  }

  /**
   * Changes the value of selectedAlbumNumber.
   *
   * @param albumNumber the number of album which is shown on the view.
   */
  public void setSelectedAlbumNumber(final int albumNumber) {
    LOGGER.info("Change select number from {} to {}",
            this.selectedAlbumNumber, albumNumber);
    this.selectedAlbumNumber = albumNumber;
    this.selectedAlbum = data.getAlbums().get(this.selectedAlbumNumber - 1);
  }

  public String getTitle() {
    return selectedAlbum.getTitle();
  }
  // other get methods are like this, which are used to get information of selected album.

  public void setTitle(final String value) {
    LOGGER.info("Change album title from {} to {}",
            selectedAlbum.getTitle(), value);
    selectedAlbum.setTitle(value);
  }
  // other set methods are like this, which are used to get information of selected album.

  /**
   * Gets a list of albums.
   *
   * @return the names of all the albums.
   */
  public String[] getAlbumList() {
    var result = new String[data.getAlbums().size()];
    for (var i = 0; i < result.length; i++) {
      result[i] = data.getAlbums().get(i).getTitle();
    }
    return result;
  }
}
```
我们可以运行类`App`来启动这个演示。复选框是专辑经典；第一个文本字段是专辑艺术家的名字；第二个是专辑名称；最后一个是作曲家的名字：
![](./etc/result.png)


## 类图
![](./etc/presentation.urm.png "presentation model")

## 适用性
在以下情况下使用表示模型模式

* 通过 GUI 窗口测试演示文稿通常很笨拙，在某些情况下甚至是不可能的。
* 不确定将使用哪个 GUI。

## 相关模式

- [Supervising Controller](https://martinfowler.com/eaaDev/SupervisingPresenter.html) 
- [Passive View](https://martinfowler.com/eaaDev/PassiveScreen.html)

## 鸣谢

* [Presentation Model Patterns](https://martinfowler.com/eaaDev/PresentationModel.html)

