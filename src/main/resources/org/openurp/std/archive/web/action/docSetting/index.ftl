[#ftl]
[@b.head/]
[@b.toolbar title="文档类型信息"/]
<div class="search-container">
  <div class="search-panel">
    [@b.form name="docSettingSearchForm" action="!search" target="docSettinglist" title="ui.searchForm" theme="search"]
      [@b.textfields names="docSetting.docType.code;代码"/]
      [@b.textfields names="docSetting.docType.name;名称"/]
      <input type="hidden" name="orderBy" value="docSetting.docType.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="docSettinglist" href="!search?orderBy=docSetting.docType.code"/]
    </div>
  </div>
[@b.foot/]
