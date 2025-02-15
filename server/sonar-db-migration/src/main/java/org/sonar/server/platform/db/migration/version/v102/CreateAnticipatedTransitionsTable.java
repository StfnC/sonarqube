/*
 * SonarQube
 * Copyright (C) 2009-2025 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.server.platform.db.migration.version.v102;

import java.sql.SQLException;
import org.sonar.db.Database;
import org.sonar.server.platform.db.migration.sql.CreateTableBuilder;
import org.sonar.server.platform.db.migration.step.CreateTableChange;
import org.sonar.server.platform.db.migration.step.DdlChange;

import static org.sonar.server.platform.db.migration.def.BigIntegerColumnDef.newBigIntegerColumnDefBuilder;
import static org.sonar.server.platform.db.migration.def.IntegerColumnDef.newIntegerColumnDefBuilder;
import static org.sonar.server.platform.db.migration.def.VarcharColumnDef.MAX_SIZE;
import static org.sonar.server.platform.db.migration.def.VarcharColumnDef.USER_UUID_SIZE;
import static org.sonar.server.platform.db.migration.def.VarcharColumnDef.UUID_SIZE;
import static org.sonar.server.platform.db.migration.def.VarcharColumnDef.newVarcharColumnDefBuilder;

public class CreateAnticipatedTransitionsTable extends CreateTableChange {
  static final String ANTICIPATED_TRANSITIONS_TABLE_NAME = "anticipated_transitions";

  public CreateAnticipatedTransitionsTable(Database db) {
    super(db, ANTICIPATED_TRANSITIONS_TABLE_NAME);
  }

  @Override
  public void execute(DdlChange.Context context, String tableName) throws SQLException {
    context.execute(new CreateTableBuilder(getDialect(), tableName)
      .addPkColumn(newVarcharColumnDefBuilder().setColumnName("uuid").setIsNullable(false).setLimit(UUID_SIZE).build())
      .addColumn(newVarcharColumnDefBuilder().setColumnName("project_uuid").setIsNullable(false).setLimit(UUID_SIZE).build())
      .addColumn(newVarcharColumnDefBuilder().setColumnName("user_uuid").setIsNullable(false).setLimit(USER_UUID_SIZE).build())
      .addColumn(newVarcharColumnDefBuilder().setColumnName("transition").setIsNullable(false).setLimit(20).build())
      .addColumn(newVarcharColumnDefBuilder().setColumnName("transition_comment").setLimit(MAX_SIZE).build())
      .addColumn(newIntegerColumnDefBuilder().setColumnName("line").build())
      .addColumn(newVarcharColumnDefBuilder().setColumnName("message").setLimit(MAX_SIZE).build())
      .addColumn(newVarcharColumnDefBuilder().setColumnName("line_hash").setLimit(255).build())
      .addColumn(newVarcharColumnDefBuilder().setColumnName("rule_key").setIsNullable(false).setLimit(200).build())
      .addColumn(newVarcharColumnDefBuilder().setColumnName("file_path").setIsNullable(false).setLimit(1500).build())
      .addColumn(newBigIntegerColumnDefBuilder().setColumnName("created_at").setIsNullable(false).build())
      .build());
  }
}
