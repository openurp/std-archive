[#ftl]
[@b.head/]
[@b.toolbar title="毕业生学业证明下载"/]
<div class="search-container">
  <div class="search-panel">
    [@b.form name="downloadSearchForm" action="!search?orderBy=graduate.graduateOn desc" target="downloadlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="graduate.std.code;学号"/]
      [@b.textfields names="graduate.std.name;姓名"/]
      [@b.textfields names="graduate.std.state.grade.code;年级"/]
      [@b.select style="width:100px" name="graduate.std.stdType.id" label="学生类别" items=stdTypes option="id,name" empty="..." /]
      [@b.date label="毕业日期" name="graduate.graduateOn" format="yyyy-MM-dd"/]
      [@b.select style="width:100px" name="graduate.std.state.department.id" label="院系" items=departments option="id,name" empty="..." /]
      <input type="hidden" name="orderBy" value="graduate.season.graduateIn desc"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="downloadlist" href="!search?orderBy=graduate.season.graduateIn desc"/]
    </div>
  </div>
[@b.foot/]
