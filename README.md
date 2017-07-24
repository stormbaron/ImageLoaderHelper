# ImageLoaderHelper
##一行代码实现Android的网络图片加载、点击查看大图、滑动放大、拖动、显示加载动画
图片的加载几乎是一款APP所必备的功能了，我们在处理图片基本会涉及到图片的网络加载、点击查看大图、能够拖动放大、滑动查看细节,同时希望在加载过程中还能有加载动画。
通过封装后，现在**一条链式代码**就能轻松实现上述所有需求,实现姿态就像使用Glide那样通过链式调用。

使用方法：
```java
ImageLoaderManager.getInstance().load(mSmallUrl)
                .setPlaceHolder(R.mipmap.ic_launcher)
                .setBigUrl(mBigUrl)
                .setIsCrossFade(true)
                .setIsSkipMemoryCache(true)
                .into(holder.mImg);
```
