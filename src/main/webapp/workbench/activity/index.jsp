<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>
<script type="text/javascript">

	$(function(){
		$("#addBtn").click(function (){
			$(".time").datetimepicker({
				minView: "month",
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left"
			});
			$.ajax({
				url:"workbench/activity/getUserList.do",
				type:"get",
				dataType:"json",
				success:function (data){
					var html = "";
					$.each(data,function (i,n){
						html+="<option value='"+n.id+"'>"+n.name+"</option>"
					})

					$("#create-owner").html(html);
					$("#create-owner").val("${user.id}")
					$("#createActivityModal").modal("show");
				}
			})
		})
		$("#saveBtn").click(function (){
			$.ajax({
				url:"workbench/activity/save.do",
				data:{  "owner":$.trim($("#create-owner").val()),
					"name":$.trim($("#create-name").val()),
					"startDate":$.trim($("#create-startDate").val()),
					"endDate":$.trim($("#create-endDate").val()),
					"cost":$.trim($("#create-cost").val()),
					"description":$.trim($("#create-description").val())
				},
				type:"post",
				dataType:"json",
				success:function (data){

					if(data.success){
						$("#activityAddForm")[0].reset();
						$("#createActivityModal").modal("hide")
					}else{
						alert("????????????")
					}
				}
			})
		})
		pageList(1,2)
		$("#searchBtn").click(function (){
			pageList(1,2)
		})
		$("#qx").click(function (){
			$("input[name='xz']").prop("checked",this.checked);
		})
        $("#activityBody").on("click","input[name='xz']",function (){
            $("#qx").prop("checked",$("input[name='xz']").length==$("input[name='xz']:checked").length);
        })

        $("#deleteBtn").click(function (){
            var $xz = $("input[name='xz']:checked");

            if ($xz.length <= 0){
                alert("??????????????????????????????")
            }else{
                var parem = "";
                for (var i = 0;i < $xz.length;i++){
                    parem += "id=" +$($xz[i]).val();
                    if(i < $xz.length-1){
                        parem += "&";
                    }

                }
                $.ajax({
                    url:"workbench/activity/deletes.do" ,
                    data: parem,
                    type:"post",
                    dataType:"json",
                    success:function (data){
                        if(data.success){
                            pageList(1,2)
                        }else{
                            alert("????????????")
                        }
                    }
                })
            }
        })
		$("#updateBtn").click(function (){
			var $xz = $("input[name='xz']:checked");
			if($xz.length == 0){
				alert("???????????????????????????");
			}else if($xz.length > 1){
				alert("?????????????????????????????????")
			}else{
				var id = $xz.val();
				$.ajax({
					url:"workbench/activity/getUser.do",
					data:{"id":id},
					type:"post",
					dataType:"json",
					success:function (data){
						var html = "";
						$.each(data.uList,function (i,n){
							html+="<option value='"+n.id+"'>"+n.name+"</option>"
						})

						$("#edit-owner").html(html);
						$("#edit-name").val(data.a.name);
						$("#edit-owner").val(data.a.owner);
						$("#edit-startDate").val(data.a.startDate);
						$("#edit-endDate").val(data.a.endDate);
						$("#edit-cost").val(data.a.cost);
						$("#edit-description").val(data.a.description);

						$("#editActivityModal").modal("show");
					}
				})
			}
		})

	})
	function pageList(pageNo,pageSize){
		$("#qx").prop("checked",false);
		$.ajax({
			url:"workbench/activity/pageList.do",
			data:{
			"pageNo":pageNo,
			"pageSize":pageSize,
			"name":$.trim($("#search-name").val()),
			"owner":$.trim($("#search-owner").val()),
			"startDeta":$.trim($("#search-startDeta").val()),
			"endDeta":$.trim($("#search-endDeta").val())
			},
			type:"get",
			dataType:"json",
			success:function (data){
				var html = "";
				$.each(data.dataList,function (i,n){
					html +='<tr class="active">';
					html +='<td><input type="checkbox" name="xz" value="'+ n.id +'"/></td>'
					html +='<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.do?id='+n.id+'\';">'+ n.name +'</a></td>'
					html +='<td>'+n.owner+'</td>';
					html +='<td>'+n.startDeta+'</td>';
					html +='<td>'+n.endDeta+'</td>';
					html +='</tr>';
				})
				var totalPages = data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;
				$("#activityBody").html(html);
				$("#activityPage").bs_pagination({
					currentPage: pageNo, // ??????
					rowsPerPage: pageSize, // ???????????????????????????
					maxRowsPerPage: 20, // ?????????????????????????????????
					totalPages: totalPages, // ?????????
					totalRows: data.total, // ???????????????

					visiblePageLinks: 3, // ??????????????????

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					onChangePage : function(event, data){
						pageList(data.currentPage , data.rowsPerPage);
					}
				})
			}
		})



	}
</script>
</head>
<body>
<!-- ????????????????????????????????? -->
<div class="modal fade" id="editActivityModal" role="dialog">
	<div class="modal-dialog" role="document" style="width: 85%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">??</span>
				</button>
				<h4 class="modal-title" id="myModalLabel2">??????????????????</h4>
			</div>
			<div class="modal-body">

				<form class="form-horizontal" role="form">

					<div class="form-group">
						<label for="edit-marketActivityOwner" class="col-sm-2 control-label">?????????<span style="font-size: 15px; color: red;">*</span></label>
						<div class="col-sm-10" style="width: 300px;">
							<select class="form-control" id="edit-owner">
								<%--<option>zhangsan</option>
								<option>lisi</option>
								<option>wangwu</option>--%>
							</select>
						</div>
						<label for="edit-marketActivityName" class="col-sm-2 control-label">??????<span style="font-size: 15px; color: red;">*</span></label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="edit-name"></input>
						</div>
					</div>

					<div class="form-group">
						<label for="edit-startTime" class="col-sm-2 control-label">????????????</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="edit-startDate" ></input>
						</div>
						<label for="edit-endTime" class="col-sm-2 control-label">????????????</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="edit-endDate" ></input>
						</div>
					</div>

					<div class="form-group">
						<label for="edit-cost" class="col-sm-2 control-label">??????</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="edit-cost"></input>
						</div>
					</div>

					<div class="form-group">
						<label for="edit-describe" class="col-sm-2 control-label">??????</label>
						<div class="col-sm-10" style="width: 81%;">
							<textarea class="form-control" rows="3" id="edit-description"></textarea>
						</div>
					</div>

				</form>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">??????</button>
				<button type="button" class="btn btn-primary" id="upBtn">??????</button>
			</div>
		</div>
	</div>
</div>
	<!-- ????????????????????????????????? -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">??</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">??????????????????</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form" id="activityAddForm">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">?????????<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">

								<select class="form-control" id="create-owner">

								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">??????<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">????????????</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startDate">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">????????????</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endDate">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">??????</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">??????</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">??????</button>
					<button type="button" class="btn btn-primary" id="saveBtn">??????</button>
				</div>
			</div>
		</div>
	</div>
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>??????????????????</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">??????</div>
				      <input class="form-control" type="text" id="search-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">?????????</div>
				      <input class="form-control" type="text" id="search-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">????????????</div>
					  <input class="form-control" type="text" id="search-startDeta" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">????????????</div>
					  <input class="form-control" type="text" id="search-endDeta">
				    </div>
				  </div>
				  
				  <button type="button" id="searchBtn" class="btn btn-default">??????</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span> ??????</button>
				  <button type="button" class="btn btn-default" id="updateBtn"><span class="glyphicon glyphicon-pencil"></span> ??????</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> ??????</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qx"/></td>
							<td>??????</td>
                            <td>?????????</td>
							<td>????????????</td>
							<td>????????????</td>
						</tr>
					</thead>
					<tbody id="activityBody">
						<%--<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">+ n.name +</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">?????????</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">
				<div id="activityPage"></div>
			</div>
			
		</div>
		
	</div>
</body>
</html>