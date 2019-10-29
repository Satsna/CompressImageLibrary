# CompressImageLibrary
图片压缩类封装

##使用方法
  
  1.Application中初始化
  
    CompressHelper.init(this);
    
  2.调用压缩方法
      /**
       * imgPathList 未压缩图片集合
       * CompressCallBack 压缩结果回调
       */
      CompressHelper.getDefault(imgPathList, new CompressCallBack() {
          @Override
          public void onFinish(List<File> compressList) {
              LogUtil.e(tag, "压缩完成=" + compressList.size());
              OkGo.<String>post(url).tag(this).params(params).execute(uploadFileCallBack);
          }
      });
