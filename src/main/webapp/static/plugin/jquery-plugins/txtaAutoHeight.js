//jQuery实现textarea高度根据内容自适应
$.fn.extend({
    txtaAutoHeight: function () {
        return this.each(function () {
            var $this = $(this);
            if (!$this.attr('initAttrH')) {
                $this.attr('initAttrH', $this.outerHeight());
            }
            // setAutoHeight(this).on('input', function () {
            //     setAutoHeight(this);
            // });
            setAutoHeight(this).on('scroll', function () {
                if ($(this).scrollTop() > 0) {
                    $(this).height(this.scrollHeight)
                }
            });
            if(!$this.html() == '') {
                $this.scrollTop(1);
            }
        });

        function setAutoHeight(elem) {
            var $obj = $(elem);
            return $obj.css({height: $obj.attr('initAttrH'), 'overflow-y': 'hidden'});
        }
    }
});

//调用
/*
$(function () {
    $("#txtaMain").txtaAutoHeight();
});
*/
