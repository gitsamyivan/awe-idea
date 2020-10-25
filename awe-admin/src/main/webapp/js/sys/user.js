$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/user/list',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', name: 'userId', index: "user_id", width: 45, key: true },
			{ label: '用户名', name: 'username', width: 75 },
			{ label: '账户名称', name: 'accountName', width: 75 },
			{ label: '账户类型', name: 'accountType', width: 80, formatter: function(value, options, row){
				if(value===0){return '<span class="label label-danger">无</span>';}
				else if(value===1){return '<span class="label label-danger">管理员</span>';}
				else if(value===2){return '<span class="label label-danger">商务</span>';}
				else if(value===3){return '<span class="label label-danger">站长</span>';}
				else if(value===4){return '<span class="label label-danger">渠道客户</span>';}
				else{
					return '<span class="label label-danger">无</span>';
				}
			}},
			{ label: '所属商务', name: 'parentAccountName', width: 75 },
			{ label: '账户余额', name: 'accountAmount', width: 75 },
			{ label: '客户具体地址', name: 'address', width: 75 },
			{ label: '邮箱', name: 'email', width: 90 },
			{ label: '手机号', name: 'mobile', width: 100 },
			{ label: '状态', name: 'status', width: 80, formatter: function(value, options, row){
				return value === 0 ? 
					'<span class="label label-danger">禁用</span>' : 
					'<span class="label label-success">正常</span>';
			}},
			{ label: '创建时间', name: 'createTime', index: "create_time", width: 80}
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			username: null
		},
		showList: true,
		updateAndSave:false,
		addFee:false,
		title:null,
		roleList:{},
		commerceList:{},
		user:{
			status:1,
			roleIdList:[]
		},
		accountTypeOption: [{ text: '无', value: '0' },{ text: '管理员', value: '1' },
		                    { text: '商务', value: '2' },{ text: '站长', value: '3'}
		                    ,{ text: '客户渠道', value: '4' }],
		accountTypeSearchOption: [{ text: '无', value: '0' },{ text: '管理员', value: '5' },
		                    { text: '商务', value: '4' },{ text: '站长', value: '2'}
		                    ,{ text: '客户渠道', value: '3' }] 
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.addFee=false;
			vm.updateAndSave=true;
			vm.title = "新增";
			vm.roleList = {};
			vm.user = {status:1,roleIdList:[]};
			
			//获取角色信息
			this.getRoleList();
			this.getCommerceList();
		},
		update: function () {
			var userId = getSelectedRow();
			if(userId == null){
				return ;
			}
			
			vm.showList = false;
			vm.addFee=false;
			vm.updateAndSave=true;
            vm.title = "修改";
			
			vm.getUser(userId);
			//获取角色信息
			this.getRoleList();
			this.getCommerceList();
		},
		fee: function () {
			var userId = getSelectedRow();
			if(userId == null){
				return ;
			}
			
			vm.showList = false;
			vm.addFee=true;
			updateAndSave=false;
            vm.title = "账户充值与金额结算";
			
			vm.getAddFeeUser(userId);
		},
		del: function () {
			var userIds = getSelectedRows();
			if(userIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../sys/user/delete",
				    data: JSON.stringify(userIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
                                vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		saveOrUpdate: function (event) {
			var url = vm.user.userId == null ? "../sys/user/save" : "../sys/user/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.user),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		addAccountFee: function (event) {
			var url = "../sys/user/addAccountFee";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.user),
			    success: function(r){
			    	if(r.code === 0){
						alert('充值或者结算成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		getUser: function(userId){
			$.get("../sys/user/info/"+userId, function(r){
				vm.user = r.user;
			});
		},
		getAddFeeUser: function(userId){
			$.get("../sys/user/addFeeinfo/"+userId, function(r){
				vm.user = r.user;
			});
		},
		getRoleList: function(){
			$.get("../sys/role/select", function(r){
				vm.roleList = r.list;
			});
		},
		getCommerceList:function(){
			$.get("../sys/user/commercelist", function(r){
				vm.commerceList = r.list;
			});
		},
		reload: function (event) {
			vm.showList = true;
			vm.addFee=false;
			vm.updateAndSave=false;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'username': vm.q.username,
                		  'accountType':vm.q.accountType},
                page:page
            }).trigger("reloadGrid");
		}
	}
});