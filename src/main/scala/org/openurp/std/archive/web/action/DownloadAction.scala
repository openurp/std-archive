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

import org.beangle.commons.codec.digest.Digests
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.doc.core.PrintOptions
import org.beangle.doc.pdf.{Encryptor, SPDConverter}
import org.beangle.security.Securities
import org.beangle.web.action.support.ServletSupport
import org.beangle.web.action.view.{Stream, View}
import org.beangle.web.servlet.url.UrlBuilder
import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.base.model.Project
import org.openurp.base.std.model.{Graduate, Student}
import org.openurp.spa.doc.config.{DocType, Orientation}
import org.openurp.starter.web.support.ProjectSupport

import java.io.File
import java.net.URI

class DownloadAction extends RestfulAction[Graduate], ServletSupport, ProjectSupport {

  override def indexSetting(): Unit = {
    given project: Project = getProject

    put("departments", getDeparts)
    val stdTypeBuilder = OqlBuilder.from(classOf[Graduate].getName, "graduate")
    stdTypeBuilder.select("distinct graduate.std.stdType")
    put("stdTypes", entityDao.search(stdTypeBuilder))
    super.indexSetting()
  }

  override def search(): View = {
    given project: Project = getProject

    put("docTypes", entityDao.getAll(classOf[DocType]))
    val builder = getQueryBuilder
    builder.where("graduate.std.state.department in (:departs)", getDeparts)
    put("graduates", entityDao.search(builder))
    forward()
  }

  private def getDocType: Option[DocType] = {
    val query = OqlBuilder.from(classOf[DocType], "doc")
    query.where("doc.code =:code", get("docType", ""))
    query.cacheable()
    val docs = entityDao.search(query)
    docs.headOption
  }

  def download(): View = {
    val doc = getDocType.get
    var doc_url = doc.adminUrl.get
    if (doc_url.startsWith("{origin}")) {
      val urlBuilder = UrlBuilder(request)
      doc_url = Strings.replace(doc_url, "{origin}", urlBuilder.buildOrigin())
    }
    val sep = if (doc_url.indexOf("?") > 0) "&" else "?"
    val graduateId = getLongId("graduate")
    val graduate = entityDao.get(classOf[Graduate], graduateId)
    val std = graduate.std
    val url = doc_url + sep + "std.id=" + graduate.std.id + "&graduate.id=" + graduateId + "&URP_SID=" + Securities.session.map(_.id).getOrElse("")
    val pdf = File.createTempFile("doc", ".pdf")
    val options = new PrintOptions
    options.orientation = doc.orientation match {
      case Orientation.Landscape => org.beangle.doc.core.Orientation.Landscape
      case _ => org.beangle.doc.core.Orientation.Portrait
    }
    SPDConverter.getInstance().convert(URI.create(url), pdf, options)
    val userPassword = if doc.enableUserPassword then Some(std.code) else None
    Encryptor.encrypt(pdf, userPassword, Digests.md5Hex(std.code + "_" + std.name))
    Stream(pdf, std.name + "_" + doc.name)
  }
}
