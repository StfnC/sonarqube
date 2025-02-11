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
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HelloWorldServiceTest {
  private ListAppender<ILoggingEvent> listAppender;

  @BeforeEach
  public void before() {
    Logger logger = (Logger) LoggerFactory.getLogger(HelloWorldService.class);

    // Testing the logging inspired by https://stackoverflow.com/a/52229629
    listAppender = new ListAppender<>();
    listAppender.start();
    logger.addAppender(listAppender);
  }

  @ParameterizedTest
  @ValueSource(strings = {"LOG8371", "TP1"})
  void greetsLOG8371(String name) {
    HelloWorldService service = new HelloWorldService();
    service.greet(name);

    List<ILoggingEvent> logs = listAppender.list;
    assertThat(logs).hasSize(1);

    ILoggingEvent log = logs.get(0);

    assertThat(log.getFormattedMessage()).isEqualTo("Hello World " + name +"!");
    assertThat(log.getLevel()).isEqualTo(Level.INFO);
  }

  @AfterEach
  public void after() {
    ((Logger) LoggerFactory.getLogger(HelloWorldService.class)).detachAndStopAllAppenders();
  }
}
