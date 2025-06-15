/*
 * Copyright (C) 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openurp.std.archive.web.action

import org.beangle.webmvc.support.action.RestfulAction
import org.beangle.webmvc.view.View
import org.openurp.base.doc.{Orientation, PageSize}
import org.openurp.base.model.Project
import org.openurp.code.std.model.StdDocType
import org.openurp.starter.web.support.ProjectSupport
import org.openurp.std.archive.config.ArchiveDocSetting

class DocSettingAction extends RestfulAction[ArchiveDocSetting], ProjectSupport {

  override def simpleEntityName: String = "docSetting"

  protected override def editSetting(entity: ArchiveDocSetting): Unit = {
    given project: Project = getProject

    put("docTypes", getCodes(classOf[StdDocType]))
    put("orientations", Orientation.values)
    put("pageSizes", PageSize.values)
  }

  override protected def saveAndRedirect(setting: ArchiveDocSetting): View = {
    setting.project = getProject
    super.saveAndRedirect(setting)
  }

}
