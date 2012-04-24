/*
 * Copyright (C) 2010 SonarSource SA
 * All rights reserved
 * mailto:contact AT sonarsource DOT com
 */
package com.sonar.sslr.squid.checks;

import com.google.common.collect.Sets;
import com.sonar.sslr.test.miniC.MiniCGrammar;
import org.junit.Test;

import java.util.Collections;
import java.util.Set;

import static com.sonar.sslr.squid.metrics.ResourceParser.scanFile;

public class AbstractNestedCommentsCheckTest {

  private static class Check extends AbstractNestedCommentsCheck<MiniCGrammar> {

    private static final Set<String> COMMENT_START_TAGS = Collections.unmodifiableSet(Sets.newHashSet("/*", "//"));

    @Override
    public Set<String> getCommentStartTags() {
      return COMMENT_START_TAGS;
    }

  }

  @Test
  public void singleLineCommentsSyntax() {
    CheckMessagesVerifier.verify(scanFile("/checks/nested_comments.mc", new Check()).getCheckMessages())
        .next().atLine(1).withMessage("This comments contains the nested comment start tag \"/*\"")
        .next().atLine(2).withMessage("This comments contains the nested comment start tag \"//\"")
        .noMore();
  }

}