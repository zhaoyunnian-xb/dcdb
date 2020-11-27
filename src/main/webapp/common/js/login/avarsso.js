/*
简要说明：
  返回数据总体结构为：
  ret: =0为失败, =1为成功
  msg: 错误原因
  username: 用户名.

主要接口：
login：登录，代码有详细说明，注意密码为md5后的密码。
logout: 退出，总是会成功的！通过回调处理或直接在函数中修改都可以
get_username: 获取当前登录的用户名
 */
var avarsso = {
  /// 单点登录地址
  sso_url: '',
  /// 不需要判断的地址
  filter: [],
  /// 当前登录的用户名
  username: '',
  /// 检查登录状态回调
  check_token_cb: null,
  set_sso_url: function(url) {
    this.sso_url = url;
  },
  add_filter: function(url) {
    this.filter.push(url);
  },
  set_check_token_cb: function(callback) {
    this.check_token_cb = callback;
  },
  get_username: function(callback) {
    if ('' != this.username) {
      callback(this.username);
    } else {
      this.set_check_token_cb(callback);
      this.checkToken(true);
    }
  },
  makeProxy: function(url, callback) {
    if (!url) {
      return null;
    }
    if (url == '') {
      return null;
    }
    var frame = document.createElement("iframe");
    frame.src = url;
    frame.style.display = "none";
    if (typeof window.addEventListener != 'undefined') {
      window.addEventListener('message', function(event) {
        document.body.removeChild(frame);
        if (callback) {
          callback(eval("("+event.data+")"));
        }
      }, false);
    } else if (typeof window.attachEvent != 'undefined') {
      //for ie
      window.attachEvent('onmessage', function(event) {
        document.body.removeChild(frame);
        if (callback) {
          callback(eval("("+event.data+")"));
        }
      });
    } else {
      alert('ie sso error');
    }
    document.body.appendChild(frame);
  },
  /**
   * [check_filter 检查是否需要需要登录]
   * @return {[boolean]} [true: 需要; false: 不需要]
   */
  check_filter: function() {
    var curUrl = window.location.pathname;
    for (var i in this.filter) {
      if (-1 !== curUrl.indexOf(this.filter[i])) {
        return false;
      }
    }
    return true;
  },
  /**
   * 请求登录
   * @param  {[string]}   username [用户名]
   * @param  {[string]}   pwd      [md5后的密码]
   * @param  {callback} callback   [登录回调]
   * @return none
   */
  login: function(username, pwd, callback) {
    var url = this.sso_url+'/index/login?username='+username+'&pwd='+pwd;
    this.makeProxy(url, function(result) {
      if (result.ret == 1) {
        avarsso.username = result.username;
      }
      callback(result);
    });
  },
  /**
   * 请求退出
   * @param  {callback} callback [退出回调]
   * @return none
   */
  logout: function(callback) {
    avarsso.makeProxy(avarsso.sso_url+'/index/logout', callback);
  },
  /**
   * 检查token是否有效, 无效则调用未登录回调
   * @param dis_filter 为true表示不判断过滤url地址
   * @return none
   */
  checkToken: function(dis_filter) {
    if (true !== dis_filter) {
      if (!avarsso.check_filter()) {
        return;
      }
    }
    avarsso.makeProxy(avarsso.sso_url, function(result) {
      if (1 == result.ret) {
        avarsso.username = result.username;
      }
      if (avarsso.check_token_cb) {
        avarsso.check_token_cb(result);
      }
    });
  },
  addLoadEvent: function(func) {
    //把现有的 window.onload 事件处理函数的值存入变量
    var oldOnload = window.onload;
    if (typeof window.onload != "function") {
      //如果这个处理函数还没有绑定任何函数，就像平时那样添加新函数
      window.onload = func;
    } else {
      //如果处理函数已经绑定了一些函数，就把新函数添加到末尾
      window.onload = function() {
        oldOnload();
        func();
      };
    }
  }
};

/// 外网测试地址为: http://oa.1gwan.com
avarsso.set_sso_url('http://10.37.0.177:8002');
avarsso.add_filter('/index/login');
avarsso.add_filter('/index/checklogin');
avarsso.set_check_token_cb(login_time_out);
function login_time_out(result) {
  if (!result.ret || result.ret!=1) {
    alert('登录超时, 请重新登录');
    window.location.href = 'http://10.37.0.177:8080/index/logout';
  } else {
    avarsso.username = result.username;
  }
}
/// 以上参数设置好，立即进行登录检测
avarsso.addLoadEvent(avarsso.checkToken);
