$(function () {
    $("#jqGrid").jqGrid({
        url: '../${requrl}/list',
        datatype: "json",
        colModel: [			
			{ label: 'logId', name: 'logId', index: 'log_id', width: 50, key: true },
			{ label: '任务id', name: 'jobId', index: 'job_id', width: 80 }, 			
			{ label: 'spring bean名称', name: 'beanName', index: 'bean_name', width: 80 }, 			
			{ label: '方法名', name: 'methodName', index: 'method_name', width: 80 }, 			
			{ label: '参数', name: 'params', index: 'params', width: 80 }, 			
			{ label: '任务状态    0：成功    1：失败', name: 'status', index: 'status', width: 80 }, 			
			{ label: '失败信息', name: 'error', index: 'error', width: 80 }, 			
			{ label: '耗时(单位：毫秒)', name: 'times', index: 'times', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }			
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
		showList: true,
		title: null,
		scheduleJobLog: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.scheduleJobLog = {};
		},
		update: function (event) {
			var logId = getSelectedRow();
			if(logId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(logId)
		},
		saveOrUpdate: function (event) {
			var url = vm.scheduleJobLog.logId == null ? "../${requrl}/save" : "../${requrl}/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.scheduleJobLog),
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
		del: function (event) {
			var logIds = getSelectedRows();
			if(logIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../${requrl}/delete",
				    data: JSON.stringify(logIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(logId){
			$.get("../${requrl}/info/"+logId, function(r){
                vm.scheduleJobLog = r.scheduleJobLog;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});