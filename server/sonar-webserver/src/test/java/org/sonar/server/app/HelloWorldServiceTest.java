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
package org.sonar.server.app;


import ch.qos.logback.classic.Level;
import org.junit.Test;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloWorldServiceTest {
  @Test
  public void greets() {
    Logger logger = (Logger) LoggerFactory.getLogger(HelloWorldService.class);

    // Testing the logging inspired by https://stackoverflow.com/a/52229629
    ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    listAppender.start();
    logger.addAppender(listAppender);

    HelloWorldService service = new HelloWorldService();
    service.greet("LOG8371");

    List<ILoggingEvent> logs = listAppender.list;
    assertThat(logs).hasSize(1);

    ILoggingEvent log = logs.get(0);

    assertThat(log.getFormattedMessage()).isEqualTo("Hello World LOG8371!");
    assertThat(log.getLevel()).isEqualTo(Level.INFO);
  }
}
