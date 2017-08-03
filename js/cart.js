new Vue({
    el: '#app',
    data: {
        items: [],
        totalMoney: 0,
        checkAllFlag: false
    },
    methods: {
        cartView: function () {
            var _this = this;
            axios.get('data/cartData.json').then(function (res) {
                _this.items = res.data.result.list;
                _this.totalMoney = res.data.result.totalMoney;
            })
        }
        ,
        changeQuantity: function (item, flag) {
            if (flag) {
                item.productQuantity++;
            } else {
                item.productQuantity--;
                if (item.productQuantity < 1) item.productQuantity = 1;
            }

        }
        ,
        itemCheck: function (item) {
            if (typeof item.checked == 'undefined') {
                this.$set(item, 'checked', true);
            } else {
                item.checked = !item.checked;
            }
        }
        ,
        checkAll: function (flag) {
            this.checkAllFlag = flag;
            var _this = this;
            this.items.forEach(function (item, index)
            {
                if (typeof item.checked == 'undefined') {
                    _this.$set(item, 'checked', true);
                } else {
                    item.checked = flag;
                }
            })
        }
    }
    ,
    mounted: function () {
        this.$nextTick(function () {
            this.cartView();
        })

    }
    ,
    filters: {
        formatMoney: function (value) {
            return '￥' + value.toFixed(2);
        }
    }
})
;