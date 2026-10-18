/*
 * Generated source: Fluxtion AOT compiler output for domain.trading.builder.TradingBuilder (DEMO project).
 * Do not edit by hand; regenerate with ./regen.sh (needs a Fluxtion API key).
 */
package com.acme.diamond.domain.trading.generated;

import com.acme.diamond.domain.trading.event.Trade;
import com.acme.diamond.domain.trading.node.LastTrade;
import com.acme.diamond.domain.trading.node.Pnl;
import com.acme.diamond.domain.trading.node.Position;
import com.acme.diamond.domain.trading.node.Risk;
import com.telamin.fluxtion.runtime.CloneableDataFlow;
import com.telamin.fluxtion.runtime.DataFlow;
import com.telamin.fluxtion.runtime.annotations.ExportService;
import com.telamin.fluxtion.runtime.annotations.OnEventHandler;
import com.telamin.fluxtion.runtime.audit.Auditor;
import com.telamin.fluxtion.runtime.audit.EventLogControlEvent;
import com.telamin.fluxtion.runtime.audit.EventLogControlEvent.LogLevel;
import com.telamin.fluxtion.runtime.audit.EventLogManager;
import com.telamin.fluxtion.runtime.audit.NodeNameAuditor;
import com.telamin.fluxtion.runtime.callback.CallbackDispatcherImpl;
import com.telamin.fluxtion.runtime.callback.ExportFunctionAuditEvent;
import com.telamin.fluxtion.runtime.callback.InternalEventProcessor;
import com.telamin.fluxtion.runtime.context.DataFlowContext;
import com.telamin.fluxtion.runtime.describe.DescriptorSupport;
import com.telamin.fluxtion.runtime.describe.ProcessorDescriptor;
import com.telamin.fluxtion.runtime.event.Event;
import com.telamin.fluxtion.runtime.input.EventFeed;
import com.telamin.fluxtion.runtime.input.SubscriptionManager;
import com.telamin.fluxtion.runtime.input.SubscriptionManagerNode;
import com.telamin.fluxtion.runtime.lifecycle.BatchHandler;
import com.telamin.fluxtion.runtime.lifecycle.Lifecycle;
import com.telamin.fluxtion.runtime.node.ForkedTriggerTask;
import com.telamin.fluxtion.runtime.node.MutableDataFlowContext;
import com.telamin.fluxtion.runtime.service.ServiceListener;
import com.telamin.fluxtion.runtime.service.ServiceRegistryNode;
import com.telamin.fluxtion.runtime.time.Clock;
import com.telamin.fluxtion.runtime.time.ClockStrategy.ClockStrategyEvent;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/**
 *
 *
 * <pre>
 * generation time           : Not available
 * api version               : 1.1.2
 * analyser version          : 1.0.79
 * target generator version  : 1.0.79
 * </pre>
 *
 * Event classes supported:
 *
 * <ul>
 *   <li>com.acme.diamond.domain.trading.event.Trade
 *   <li>com.telamin.fluxtion.runtime.audit.EventLogControlEvent
 *   <li>com.telamin.fluxtion.runtime.time.ClockStrategy.ClockStrategyEvent
 * </ul>
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class TradingProcessor
    implements CloneableDataFlow<TradingProcessor>,
        /*--- @ExportService start ---*/
        @ExportService ServiceListener,
        /*--- @ExportService end ---*/
        DataFlow,
        InternalEventProcessor,
        BatchHandler,
        com.telamin.fluxtion.runtime.node.NodeNameLookup {

  //Node declarations
  private final transient CallbackDispatcherImpl callbackDispatcher = new CallbackDispatcherImpl();
  public final transient Clock clock = new Clock();
  public final transient EventLogManager eventLogger = new EventLogManager();
  public final transient NodeNameAuditor nodeNameLookup = new NodeNameAuditor();
  private final transient SubscriptionManagerNode subscriptionManager =
      new SubscriptionManagerNode();
  private final transient MutableDataFlowContext context =
      new com.telamin.fluxtion.runtime.node.MutableDataFlowContext(
          nodeNameLookup, callbackDispatcher, subscriptionManager, callbackDispatcher);;
  public final transient ServiceRegistryNode serviceRegistry = new ServiceRegistryNode();
  public final transient LastTrade trade = new LastTrade();
  public final transient Pnl pnl = new com.acme.diamond.domain.trading.node.Pnl(trade);;
  public final transient Position position =
      new com.acme.diamond.domain.trading.node.Position(trade);;
  public final transient Risk risk = new com.acme.diamond.domain.trading.node.Risk(position, pnl);;
  private final transient ExportFunctionAuditEvent functionAudit = new ExportFunctionAuditEvent();
  //Dirty flags
  private boolean initCalled = false;
  private boolean processing = false;
  private boolean buffering = false;
  //M50/W1 - written by CallbackDispatcherImpl when it queues, cleared when it drains empty. Read on
  //the event path instead of walking processor->dispatcher->ArrayDeque to be told the queue is empty.
  //Measured saving on a 3-event-type graph: 0.098ns of a 5.61ns event on a JIT; nothing on native+PGO.
  private boolean callbacksPending = false;
  private final transient IdentityHashMap<Object, BooleanSupplier> dirtyFlagSupplierMap =
      new IdentityHashMap<>(3);
  private final transient IdentityHashMap<Object, Consumer<Boolean>> dirtyFlagUpdateMap =
      new IdentityHashMap<>(3);

  private boolean isDirty_pnl = false;
  private boolean isDirty_position = false;
  private boolean isDirty_trade = false;

  //Forked declarations

  //Filter constants

  //Self-description of the embeddable surface — see ProcessorDescriptor
  private static final ProcessorDescriptor DESCRIPTOR =
      DescriptorSupport.of(
          TradingProcessor.class,
          TradingProcessor::new,
          new ProcessorDescriptor.Input[] {
            new ProcessorDescriptor.Input(
                "Trade", "com.acme.diamond.domain.trading.event.Trade", false)
          },
          new ProcessorDescriptor.Sink[] {},
          new ProcessorDescriptor.Service[] {},
          new DescriptorSupport.Meta(
              null,
              "1.0.79",
              "56a63b755fead4d8d7b1090d4fa5122ddae4385b04b348c5d65afa153c0acf0f",
              null));

  @Override
  public ProcessorDescriptor getDescriptor() {
    return DESCRIPTOR;
  }

  //unknown event handler
  private Consumer unKnownEventHandler = (e) -> {};

  public TradingProcessor(Map<Object, Object> contextMap) {
    if (context != null) {
      context.replaceMappings(contextMap);
    }
    eventLogger.setClearAfterPublish(false);
    eventLogger.trace = true;
    eventLogger.printEventToString = true;
    eventLogger.printThreadName = true;
    eventLogger.traceLevel = LogLevel.INFO;
    eventLogger.clock = clock;
    eventLogger.binaryRecord = false;
    eventLogger.recordEndTime = true;
    context.setClock(clock);
    serviceRegistry.setDataFlowContext(context);
    //node auditors
    initialiseAuditor(clock);
    initialiseAuditor(eventLogger);
    initialiseAuditor(nodeNameLookup);
    initialiseAuditor(serviceRegistry);
    if (subscriptionManager != null) {
      subscriptionManager.setSubscribingEventProcessor(this);
    }
    if (context != null) {
      context.setEventProcessorCallback(this);
    }
  }

  public TradingProcessor() {
    this(null);
  }

  @Override
  public void init() {
    initCalled = true;
    auditEvent(Lifecycle.LifecycleEvent.Init);
    //initialise dirty lookup map
    isDirty("test");
    clock.init();
    afterEvent();
  }

  @Override
  public void start() {
    if (!initCalled) {
      throw new RuntimeException("init() must be called before start()");
    }
    processing = true;
    auditEvent(Lifecycle.LifecycleEvent.Start);

    afterEvent();
    callbackDispatcher.dispatchQueuedCallbacks();
    processing = false;
  }

  @Override
  public void startComplete() {
    if (!initCalled) {
      throw new RuntimeException("init() must be called before startComplete()");
    }
    processing = true;
    auditEvent(Lifecycle.LifecycleEvent.StartComplete);

    afterEvent();
    callbackDispatcher.dispatchQueuedCallbacks();
    processing = false;
  }

  @Override
  public void stop() {
    if (!initCalled) {
      throw new RuntimeException("init() must be called before stop()");
    }
    processing = true;
    auditEvent(Lifecycle.LifecycleEvent.Stop);

    afterEvent();
    callbackDispatcher.dispatchQueuedCallbacks();
    processing = false;
  }

  @Override
  public void tearDown() {
    initCalled = false;
    auditEvent(Lifecycle.LifecycleEvent.TearDown);
    serviceRegistry.tearDown();
    nodeNameLookup.tearDown();
    eventLogger.tearDown();
    clock.tearDown();
    subscriptionManager.tearDown();
    afterEvent();
  }

  @Override
  public void setContextParameterMap(Map<Object, Object> newContextMapping) {
    context.replaceMappings(newContextMapping);
  }

  @Override
  public void addContextParameter(Object key, Object value) {
    context.addMapping(key, value);
  }

  //EVENT DISPATCH - START
  @Override
  public void onEvent(Object event) {
    processEvent(event);
  }

  private void processEvent(Object event) {
    if (buffering) {
      triggerCalculation();
    }
    if (processing) {
      callbackDispatcher.queueReentrantEvent(event);
    } else {
      processing = true;
      onEventInternal(event);
      if (callbacksPending) {
        final boolean sharedBefore = clock.shareReading(true);
        callbackDispatcher.dispatchQueuedCallbacks();
        clock.shareReading(sharedBefore);
      }
      processing = false;
    }
  }

  /**
   * M50/W1 - the dispatcher tells this processor when it has queued work, and when the queue has
   * drained empty. Keeping the answer in a field this processor owns is what lets the event path
   * skip walking into the dispatcher and its ArrayDeque on every event to be told there is nothing
   * to do. The dispatcher owns the WRITE because it sees every queueing path - a node holding the
   * dispatcher directly can queue without this processor ever seeing the call.
   */
  @Override
  public void callbacksPending(boolean pending) {
    callbacksPending = pending;
  }

  @Override
  public void onEventInternal(Object event) {
    if (event instanceof Trade) {
      Trade typedEvent = (Trade) event;
      handleEvent(typedEvent);
    } else if (event instanceof EventLogControlEvent) {
      EventLogControlEvent typedEvent = (EventLogControlEvent) event;
      handleEvent(typedEvent);
    } else if (event instanceof ClockStrategyEvent) {
      ClockStrategyEvent typedEvent = (ClockStrategyEvent) event;
      handleEvent(typedEvent);
    } else {
      unKnownEventHandler(event);
    }
  }

  @OnEventHandler(failBuildIfMissingBooleanReturn = false)
  public void onEvent(Trade event) {
    processEvent(event);
  }

  @OnEventHandler(failBuildIfMissingBooleanReturn = false)
  public void onEvent(EventLogControlEvent event) {
    processEvent(event);
  }

  @OnEventHandler(failBuildIfMissingBooleanReturn = false)
  public void onEvent(ClockStrategyEvent event) {
    processEvent(event);
  }

  public void handleEvent(Trade typedEvent) {
    auditEvent(typedEvent);
    //Default, no filter methods
    auditInvocation(trade, "trade", "onTrade", "OnEventHandler", typedEvent);
    isDirty_trade = trade.onTrade(typedEvent);
    if (guardCheck_pnl()) {
      auditInvocation(pnl, "pnl", "recalculate", "OnTrigger", typedEvent);
      isDirty_pnl = pnl.recalculate();
    }
    if (guardCheck_position()) {
      auditInvocation(position, "position", "recalculate", "OnTrigger", typedEvent);
      isDirty_position = position.recalculate();
    }
    if (guardCheck_risk()) {
      auditInvocation(risk, "risk", "recalculate", "OnTrigger", typedEvent);
      risk.recalculate();
    }
    afterEvent();
  }

  public void handleEvent(EventLogControlEvent typedEvent) {
    auditEvent(typedEvent);
    //Default, no filter methods
    auditInvocation(
        eventLogger, "eventLogger", "calculationLogConfig", "OnEventHandler", typedEvent);
    eventLogger.calculationLogConfig(typedEvent);
    afterEvent();
  }

  public void handleEvent(ClockStrategyEvent typedEvent) {
    auditEvent(typedEvent);
    //Default, no filter methods
    auditInvocation(clock, "clock", "setClockStrategy", "OnEventHandler", typedEvent);
    clock.setClockStrategy(typedEvent);
    afterEvent();
  }
  //EVENT DISPATCH - END

  //EXPORTED SERVICE FUNCTIONS - START
  @Override
  public void deRegisterService(com.telamin.fluxtion.runtime.service.Service<?> arg0) {
    beforeServiceCall(
        "@Override\npublic void deRegisterService(com.telamin.fluxtion.runtime.service.Service<?> arg0)");
    ExportFunctionAuditEvent typedEvent = functionAudit;
    auditInvocation(
        serviceRegistry, "serviceRegistry", "deRegisterService", "ExportService", typedEvent);
    serviceRegistry.deRegisterService(arg0);
    afterServiceCall();
  }

  @Override
  public void registerService(com.telamin.fluxtion.runtime.service.Service<?> arg0) {
    beforeServiceCall(
        "@Override\npublic void registerService(com.telamin.fluxtion.runtime.service.Service<?> arg0)");
    ExportFunctionAuditEvent typedEvent = functionAudit;
    auditInvocation(
        serviceRegistry, "serviceRegistry", "registerService", "ExportService", typedEvent);
    serviceRegistry.registerService(arg0);
    afterServiceCall();
  }
  //EXPORTED SERVICE FUNCTIONS - END

  //EVENT BUFFERING - START
  public void bufferEvent(Object event) {
    buffering = true;
    if (event instanceof Trade) {
      Trade typedEvent = (Trade) event;
      auditEvent(typedEvent);
      auditInvocation(trade, "trade", "onTrade", "OnEventHandler", typedEvent);
      isDirty_trade = trade.onTrade(typedEvent);
    } else if (event instanceof EventLogControlEvent) {
      EventLogControlEvent typedEvent = (EventLogControlEvent) event;
      auditEvent(typedEvent);
      auditInvocation(
          eventLogger, "eventLogger", "calculationLogConfig", "OnEventHandler", typedEvent);
      eventLogger.calculationLogConfig(typedEvent);
    } else if (event instanceof ClockStrategyEvent) {
      ClockStrategyEvent typedEvent = (ClockStrategyEvent) event;
      auditEvent(typedEvent);
      auditInvocation(clock, "clock", "setClockStrategy", "OnEventHandler", typedEvent);
      clock.setClockStrategy(typedEvent);
    }
  }

  public void triggerCalculation() {
    buffering = false;
    String typedEvent = "No event information - buffered dispatch";
    if (guardCheck_pnl()) {
      auditInvocation(pnl, "pnl", "recalculate", "OnTrigger", typedEvent);
      isDirty_pnl = pnl.recalculate();
    }
    if (guardCheck_position()) {
      auditInvocation(position, "position", "recalculate", "OnTrigger", typedEvent);
      isDirty_position = position.recalculate();
    }
    if (guardCheck_risk()) {
      auditInvocation(risk, "risk", "recalculate", "OnTrigger", typedEvent);
      risk.recalculate();
    }
    afterEvent();
  }
  //EVENT BUFFERING - END

  private void auditEvent(Object typedEvent) {
    clock.eventReceived(typedEvent);
    eventLogger.eventReceived(typedEvent);
  }

  private void auditEvent(Event typedEvent) {
    clock.eventReceived(typedEvent);
    eventLogger.eventReceived(typedEvent);
  }

  private void auditInvocation(
      Object node, String nodeName, String methodName, String annotation, Object typedEvent) {
    eventLogger.nodeInvoked(node, nodeName, methodName, annotation, typedEvent);
  }

  private void initialiseAuditor(Auditor auditor) {
    auditor.init();
    auditor.nodeRegistered(trade, "trade");
    auditor.nodeRegistered(pnl, "pnl");
    auditor.nodeRegistered(position, "position");
    auditor.nodeRegistered(risk, "risk");
    auditor.nodeRegistered(callbackDispatcher, "callbackDispatcher");
    auditor.nodeRegistered(subscriptionManager, "subscriptionManager");
    auditor.nodeRegistered(context, "context");
  }

  private void beforeServiceCall(String functionDescription) {
    functionAudit.setFunctionDescription(functionDescription);
    auditEvent(functionAudit);
    if (buffering) {
      triggerCalculation();
    }
    processing = true;
  }

  private void afterServiceCall() {
    afterEvent();
    callbackDispatcher.dispatchQueuedCallbacks();
    processing = false;
  }

  /**
   * DataFlow.runInEventCycle: run a host's action as an event cycle, with auditEvent as its audit
   * context. A buffered calculation runs first (it closes its own record), then the cycle opens
   * with the caller's event and the action runs; a finally closes the cycle and dispatches what the
   * action queued, and an inner finally clears processing, so a throw cannot wedge the processor.
   * auditEvent is dispatched to no node and marks nothing dirty. An auditEvent that is an Event
   * supplies its own event time, as on the event path; any other object takes the process time. Not
   * re-entrant. To disable the path in a generated processor, make this throw.
   *
   * <p>No @Override, deliberately: generated source must also compile against a runtime that
   * predates DataFlow.runInEventCycle (1.0.16), where this is an ordinary public method a host
   * finds on the class. From the runtime that declares it, it overrides the interface default by
   * signature. GeneratedSourceOnOldRuntimeTest compiles freshly generated source against the oldest
   * supported runtime so this cannot silently regress.
   */
  public void runInEventCycle(Object auditEvent, Runnable action) {
    if (processing) {
      throw new IllegalStateException(
          "runInEventCycle is not re-entrant: it was called inside an event cycle");
    }
    if (buffering) {
      triggerCalculation();
    }
    processing = true;
    try {
      // an Event supplies its own event time, as on the event path: the static type selects the auditors'
      // overload, eventReceived(Event) or eventReceived(Object)
      if (auditEvent instanceof com.telamin.fluxtion.runtime.event.Event) {
        auditEvent((com.telamin.fluxtion.runtime.event.Event) auditEvent);
      } else {
        auditEvent(auditEvent);
      }
      action.run();
    } finally {
      // closed even when the action throws, as a host's own audit bracket closes its record; processing is
      // cleared innermost, so a throw from the close cannot wedge the processor either
      try {
        afterEvent();
        callbackDispatcher.dispatchQueuedCallbacks();
      } finally {
        processing = false;
      }
    }
  }

  private void afterEvent() {
    clock.processingComplete();
    eventLogger.processingComplete();
    isDirty_pnl = false;
    isDirty_position = false;
    isDirty_trade = false;
  }

  @Override
  public void batchPause() {
    auditEvent(Lifecycle.LifecycleEvent.BatchPause);
    processing = true;

    afterEvent();
    callbackDispatcher.dispatchQueuedCallbacks();
    processing = false;
  }

  @Override
  public void batchEnd() {
    auditEvent(Lifecycle.LifecycleEvent.BatchEnd);
    processing = true;

    afterEvent();
    callbackDispatcher.dispatchQueuedCallbacks();
    processing = false;
  }

  @Override
  public boolean isDirty(Object node) {
    return dirtySupplier(node).getAsBoolean();
  }

  @Override
  public BooleanSupplier dirtySupplier(Object node) {
    if (dirtyFlagSupplierMap.isEmpty()) {
      dirtyFlagSupplierMap.put(pnl, () -> isDirty_pnl);
      dirtyFlagSupplierMap.put(position, () -> isDirty_position);
      dirtyFlagSupplierMap.put(trade, () -> isDirty_trade);
    }
    return dirtyFlagSupplierMap.getOrDefault(node, DataFlow.ALWAYS_FALSE);
  }

  @Override
  public void setDirty(Object node, boolean dirtyFlag) {
    if (dirtyFlagUpdateMap.isEmpty()) {
      dirtyFlagUpdateMap.put(pnl, (b) -> isDirty_pnl = b);
      dirtyFlagUpdateMap.put(position, (b) -> isDirty_position = b);
      dirtyFlagUpdateMap.put(trade, (b) -> isDirty_trade = b);
    }
    dirtyFlagUpdateMap.get(node).accept(dirtyFlag);
  }

  private boolean guardCheck_pnl() {
    return isDirty_trade;
  }

  private boolean guardCheck_position() {
    return isDirty_trade;
  }

  private boolean guardCheck_risk() {
    return isDirty_pnl | isDirty_position;
  }

  /**
   * M50/W4 — nodes resolved by a generated switch, not by a populated map: registering them would
   * publish every node into the auditor's HashMaps and stop the graph being dissolved.
   */
  @SuppressWarnings("unchecked")
  @Override
  public <T> T getInstanceById(String id) throws NoSuchFieldException {
    switch (id) {
      case "trade":
        return (T) trade;
      case "pnl":
        return (T) pnl;
      case "position":
        return (T) position;
      case "risk":
        return (T) risk;
      case "eventLogger":
        return (T) eventLogger;
      case "nodeNameLookup":
        return (T) nodeNameLookup;
      case "callbackDispatcher":
        return (T) callbackDispatcher;
      case "subscriptionManager":
        return (T) subscriptionManager;
      case "context":
        return (T) context;
      case "serviceRegistry":
        return (T) serviceRegistry;
      case "clock":
        return (T) clock;
      default:
        throw new NoSuchFieldException(id);
    }
  }

  /** M50/W4 — the reverse direction, also generated. */
  @Override
  public String lookupInstanceName(Object node) {
    if (node == trade) {
      return "trade";
    }
    if (node == pnl) {
      return "pnl";
    }
    if (node == position) {
      return "position";
    }
    if (node == risk) {
      return "risk";
    }
    if (node == eventLogger) {
      return "eventLogger";
    }
    if (node == nodeNameLookup) {
      return "nodeNameLookup";
    }
    if (node == callbackDispatcher) {
      return "callbackDispatcher";
    }
    if (node == subscriptionManager) {
      return "subscriptionManager";
    }
    if (node == context) {
      return "context";
    }
    if (node == serviceRegistry) {
      return "serviceRegistry";
    }
    if (node == clock) {
      return "clock";
    }
    return null;
  }

  @Override
  public <T> T getNodeById(String id) throws NoSuchFieldException {
    try {
      return getInstanceById(id);
    } catch (NoSuchFieldException miss) {
      // Auditors live on the SEP as fields rather than in nodeNameLookup, so callers
      // (especially DataFlow.getServiceById) get one unified lookup path. The auditor
      // half is a generated switch, not a reflective probe: reflection here would
      // require native-image reflection configuration from every user, and would fail
      // at runtime rather than at build time.
      try {
        @SuppressWarnings("unchecked")
        T t = (T) getAuditorById(id);
        return t;
      } catch (NoSuchFieldException stillMissing) {
        throw miss;
      }
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A extends Auditor> A getAuditorById(String id) throws NoSuchFieldException {
    switch (id) {
      case "clock":
        return (A) clock;
      case "eventLogger":
        return (A) eventLogger;
      case "nodeNameLookup":
        return (A) nodeNameLookup;
      case "serviceRegistry":
        return (A) serviceRegistry;
      default:
        throw new NoSuchFieldException(id);
    }
  }

  @Override
  public void addEventFeed(EventFeed eventProcessorFeed) {
    subscriptionManager.addEventProcessorFeed(eventProcessorFeed);
  }

  @Override
  public void removeEventFeed(EventFeed eventProcessorFeed) {
    subscriptionManager.removeEventProcessorFeed(eventProcessorFeed);
  }

  @Override
  public TradingProcessor newInstance() {
    return new TradingProcessor();
  }

  @Override
  public TradingProcessor newInstance(Map<Object, Object> contextMap) {
    return new TradingProcessor();
  }

  @Override
  public String getLastAuditLogRecord() {
    try {
      EventLogManager eventLogManager = getAuditorById(EventLogManager.NODE_NAME);
      return eventLogManager.lastRecordAsString();
    } catch (Throwable e) {
      return "";
    }
  }

  public void unKnownEventHandler(Object object) {
    unKnownEventHandler.accept(object);
  }

  @Override
  public <T> void setUnKnownEventHandler(Consumer<T> consumer) {
    unKnownEventHandler = consumer;
  }

  @Override
  public SubscriptionManager getSubscriptionManager() {
    return subscriptionManager;
  }
}
