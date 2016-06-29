# Android版作品集
产品名称：作品集

https://github.com/achaly/ox

## APP功能简介
作品是什么？作品可以是我们的摄影图片，可以是自己拍的小视频或微电影，可以是自己录制的歌曲或着有趣的音频。通过作品集这款产品我们可以查看别人的作品，发布自己的作品。我们还能关注一些作者，关注该作者的作品。这个app就像一个潘多拉魔盒，透过它能够看到人们头脑中的奇点，看到人们用心创作的艺术。

# APP预览
<img src="https://github.com/achaly/ox/blob/master/screenshot/7.pic.jpg" width="320" height="570">
<img src="https://github.com/achaly/ox/blob/master/screenshot/6.pic.jpg" width="320" height="570">
<img src="https://github.com/achaly/ox/blob/master/screenshot/5.pic.jpg" width="320" height="570">
<img src="https://github.com/achaly/ox/blob/master/screenshot/1.pic.jpg" width="320" height="570">
<img src="https://github.com/achaly/ox/blob/master/screenshot/4.pic.jpg" width="320" height="570">
<img src="https://github.com/achaly/ox/blob/master/screenshot/3.pic.jpg" width="320" height="570">
<img src="https://github.com/achaly/ox/blob/master/screenshot/2.pic.jpg" width="320" height="570">

## Android版本程序架构介绍
作品集功能需求：作品列表，作品展示，发布作品，查看某位作者信息和作品，关注某位作者，发私信给关注的作者。

Android程序采用Clean架构，主要分3层：实体层，基础业务层，表现层。

实体层包含User和Status类。User类是对作者的封装，UserOperation接口包含了作者的操作集合。
Status类是对作品的封装。
实体的技术底层实现使用LeanCloud，User和Status对LeanCloud接口做了封装。

基础业务类包含数据的存储、上传、下载，账户的基本功能等。主要在help包。
作品的具体内容使用Qiniu云存储，用户看到的图片，视频和音频都是存储在Qiniu云上。
AccountHelper封装了LeanCloud的API，提供了账户的基本操作。
StorageHelper封装了Qiniu的API，提供数据上传功能。
ImageLoader封装了图片加载API，提供加载图片功能。
network包下的类封装了基础的网络操作。
后续会根据具体业务需求增加新的基础业务逻辑。

表现层主要是展示UI，这块使用Android的基础组件完成，见ui包。

APP中还使用了很多android常用的library，如RxJava，ButterKnife，picasso等。具体见源码。